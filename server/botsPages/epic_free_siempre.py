import urllib
from bs4 import BeautifulSoup
import pyrebase
import os
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
from PIL import Image
from io import BytesIO
import requests
import time

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
storage = firebase.storage()
############ CREAMOS EL OBJETO JUEGO #################
class Juego():
    def __init__(self, nombre, descripcion, my_url_image, generos, url_origen):
        self.nombre = nombre
        self.nombre_min = nombre.lower()
        self.descripcion = descripcion
        self.my_db_image = guardarImagen(my_url_image, nombre)
        self.generos = generos
        self.plataforma = 'pc'
        self.url_origen = url_origen
    
    def __str__(self):
        return ("""el juego %s con fechas %s estado %s"""%(self.nombre, self.fecha , self.estado))

    def __json__(self):
        return {'nombre': self.nombre, 'nombreMin': self.nombre_min , 'descripcion': self.descripcion,
        'image_url': self.my_db_image, 'generos': self.generos,
        'url_origen': self.url_origen ,'plataforma': self.plataforma}

def guardarImagen(image_url, nombre):
    nombre = nombre.replace('/', '*').replace(' ','')
    image_object = requests.get(image_url)
    image = Image.open(BytesIO(image_object.content))
    image.save("img/epic/" + nombre + "." + image.format, image.format)
    nombre_juego = nombre + "." + image.format
    storage.child('epic_free/'+nombre_juego).put("img/epic/" + nombre_juego)
    url_my_db = storage.child('epic_free/'+ nombre_juego).get_url(None)
    return url_my_db

def sacarJuego(nombre, img_url, url_juego):
    try:
        html = urllib.request.urlopen(url_juego)
        soup = BeautifulSoup(html, "lxml")
        divs = soup.find_all('div', class_='css-h8dgd-AboutSectionLayout__row')
        spans = divs[2].find_all('span')
        generos = []
        for span in spans:
            generos.append(span.text)
        div = soup.find('div', class_='css-pfxkyb')
        descripcion = div.text
        return Juego(nombre, descripcion, img_url, generos, url_juego)
    except:
        return None

# INICIAMOS SCRAPING
cont = 0
lista_juegos = []
for i in range(0,1):
    url = 'https://www.epicgames.com/store/es-ES/browse?sortBy=releaseDate&sortDir=DESC&priceTier=tierFree&count=40&start='+ str(cont)
    url_base= 'https://www.epicgames.com'
    try:
        chromeOptions = webdriver.ChromeOptions()
        prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
        chromeOptions.add_experimental_option("prefs",prefs)
        driver = webdriver.Chrome("chromedriver",options=chromeOptions)
        driver.get(url)
    except Exception as e:
        print(e)


    WebDriverWait(driver,20).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR,'img[class="css-1h7nwzt-Picture-styles__image-OfferCardImageArt__picture-OfferCardImagePortrait__picture-Picture-styles__visible"')))
    time.sleep(5)
    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
    soup = BeautifulSoup(html, "lxml")
    driver.quit()

    aes = soup.find_all('a', class_='css-1a48279-DiscoverCardLayout__link')
    imgs = soup.find_all('img', class_="css-1h7nwzt-Picture-styles__image-OfferCardImageArt__picture-OfferCardImagePortrait__picture-Picture-styles__visible")
    for i in range(0,len(imgs)):
        nombre = imgs[i]['alt']
        img_url = imgs[i]['data-image']
        url_juego = url_base + aes[i]['href']
        juego = sacarJuego(nombre, img_url, url_juego)
        if juego is not None:
            lista_juegos.append(juego)
    cont += 40

# SUBIMOS A FIREBASE
db = firebase.database()
db.child('gratis').child('epic').remove()
for j in lista_juegos:
    db.child("gratis").child("epic").push(j.__json__())
