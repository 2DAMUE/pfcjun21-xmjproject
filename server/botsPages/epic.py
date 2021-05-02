import urllib
from bs4 import BeautifulSoup
import os
import time
from random import randint
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
import pyrebase
import json
from PIL import Image
from io import BytesIO
import requests


###### ABRIMOS FIREBASE #######
config = {
    "apiKey": "AIzaSyAsxaPfthuvrzCp2DCL0Sz4Fs1XzDtd7hg",
    "authDomain": "gamesource-9bc51.firebaseapp.com",
    "databaseURL": "https://gamesource-9bc51-default-rtdb.europe-west1.firebasedatabase.app",
    "projectId": "gamesource-9bc51",
    "storageBucket": "gamesource-9bc51.appspot.com",
    "serviceAccount": "json/gamesource-9bc51-firebase-adminsdk-l31g4-e0ec60fda2.json",
    "messagingSenderId": "204253438311",
    "appId": "1:204253438311:web:fb99cf095a8bee96b10fc2"
}

firebase = pyrebase.initialize_app(config)

############ CREAMOS EL OBJETO JUEGO #################
class Juego():
    def __init__(self, nombre, fecha, estado, descripcion, my_db_image, generos):
        self.nombre = nombre
        self.fecha = fecha
        self.estado = estado
        self.descripcion = descripcion
        self.my_db_image = my_db_image
        self.generos = generos
    
    def __str__(self):
        return ("""el juego %s con fechas %s estado %s"""%(self.nombre, self.fecha , self.estado))


def sacar_datos(datos, url, image_url):
    estado = True
    if(datos[0].lower().find("gratis")):
        estado = False
    
    nombre = datos[1]
    if estado == True:
        fecha = [-1, datos[2].split(' - ')[1]]
    else:
        fecha = [datos[2].split(' - ')[0], datos[2].split(' - ')[1]]
    
    descripcion = obtenerDescripcion(url)
    generos = obtenerGeneros(url)
    my_db_image = guardarImagen(image_url, nombre)
    juego = Juego(nombre, fecha, estado, descripcion,my_db_image, generos)
    return juego

def obtenerGeneros(url):
    html = urllib.request.urlopen(url)
    soup = BeautifulSoup(html, "lxml")
    divs = soup.find_all('div', class_='css-yqbyl2-GameMeta-styles__items')
    spans = divs[2].find_all('span')
    generos = []
    for span in spans:
        generos.append(span.text)

    return generos

def guardarImagen(image_url, nombre):
    image_object = requests.get(image_url)
    image = Image.open(BytesIO(image_object.content))
    image.save("img/epic/" + nombre + "." + image.format, image.format)
    storage = firebase.storage()
    nombre_juego = nombre + "." + image.format
    storage.child('epic_free/'+nombre_juego).put("img/epic/" + nombre_juego)
    url_my_db = storage.child('epic_free/'+ nombre_juego).get_url(None)
    return url_my_db

def obtenerDescripcion(url):
    html = urllib.request.urlopen(url)
    soup = BeautifulSoup(html, "lxml")

    div = soup.find('div', class_='css-167ntke')
    descripcion = div.text
    return descripcion

def getUrlJuegos(sections):
    urls = []
    urlbase = 'https://www.epicgames.com'
    for a in sections[1].find_all('a', href= True): 
        src = a['href']
        urls.append(urlbase + src)
    return urls
     
########### COMIENZA LA CONEXION #####################
url = 'https://www.epicgames.com/store/es-ES/'

try:
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
    chromeOptions.add_experimental_option("prefs",prefs)
    driver = webdriver.Chrome("chromedriver",options=chromeOptions)
    driver.get(url)
except Exception as e:
    print(e)

WebDriverWait(driver,5).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="css-shu77l"')))
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()
section = soup.find_all('section', class_='css-1nzrk0w-CardGrid-styles__groupWrapper')
divs = soup.find_all("div", class_="css-shu77l")
imgs = soup.find_all('img',class_='css-1s4ypbt-Picture-styles__image-OfferCardImageArt__picture-OfferCardImageLandscape__picture-Picture-styles__visible')
juegosLista = []
cont = 0
urls_juegos = getUrlJuegos(section)
for div in divs:
    datos_juego = []
    spans = div.find_all('span')
    image_url_page = imgs[cont]['data-image']
    for span in spans:
        datos_juego.append(span.text)
    juegosLista.append(sacar_datos(datos_juego, urls_juegos[cont], image_url_page))
    cont += 1
    
########## A partir de aqui subimos los datos a firebase #############


db = firebase.database()
db.child('gratis').child('epic').remove()
for j in juegosLista:
    juego = {'nombre':j.nombre, 'fechas': [j.fecha[0],j.fecha[1]], 'estado': j.estado , 
    'descripcion': j.descripcion, 'image_url': j.my_db_image, 'generos': j.generos}
    db.child("gratis").child("epic").push(juego)
