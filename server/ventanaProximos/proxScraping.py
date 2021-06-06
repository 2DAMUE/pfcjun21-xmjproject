from bs4 import BeautifulSoup
from random import randint
import os
import pyrebase
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
import time
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
storage = firebase.storage()

class Juego():
    def __init__(self, nombre, descripcion, img_url, generos, fecha_salida, plataformas):
        self.nombre = nombre
        self.nombre_min = nombre.lower()
        self.descripcion = descripcion
        self.my_db_image = guardarImagen(img_url, nombre)
        self.generos = generos
        self.fecha_salida = fecha_salida
        self.plataformas = plataformas
        
    def __json__(self):
        return {'nombre': self.nombre, 'nombreMin': self.nombre_min ,'descripcion': self.descripcion, 'image_url': self.my_db_image,
         'generos': self.generos, 'fecha_salida': self.fecha_salida, 'plataformas': self.plataformas}

# CREAMOS METODOS PARA EXTRAER DATOS
def guardarImagen(image_url, nombreJ):
    nombreJ = nombreJ.replace('/', '*').replace(' ','')
    image_object = requests.get(image_url)
    image = Image.open(BytesIO(image_object.content))
    image.save("img/proximos/" + nombreJ + "." + image.format, image.format)
    nombre_juego = nombreJ + "." + image.format
    storage.child('proximos/'+nombre_juego).put("img/proximos/" + nombre_juego)
    url_my_db = storage.child('proximos/'+ nombre_juego).get_url(None)
    return url_my_db

def cadaJuego(urlJuego, nombre, descripcion, plataformas, fecha_salida):
    time.sleep(randint(2,10))
    try:
        chromeOptions = webdriver.ChromeOptions()
        prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
        chromeOptions.add_experimental_option("prefs",prefs)
        chromeOptions.add_argument('--disable-blink-features=AutomationControlled')
        driver = webdriver.Chrome("chromedriver",options=chromeOptions)
        driver.get(urlJuego)
    except Exception as e:
        print(e)

    #esperamos aque carge la caja de los juegos
    WebDriverWait(driver,40).until(EC.presence_of_element_located((By.CSS_SELECTOR,'figure[class="ga-co"')))
    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
    soup = BeautifulSoup(html, "lxml")
    driver.quit()

    figure = soup.find('figure', class_='ga-co')
    img_url ='https:'+(figure.find('img'))['src']
    genString = soup.find('span', class_='val').text
    generos = genString.split(', ')
    '''
    generos = []
    for gen in gens:
        generos.append(gen.trim())
    '''
    plataformas_bien = traducirPlataformas(plataformas)
    return Juego(nombre, descripcion, img_url, generos, fecha_salida, plataformas_bien)
    
def traducirPlataformas(plataformas):
    dictPlataformas = {'NSW':'Switch', 'XBO':'Xbox One', 'STD':'Stadia', 'PC':'PC', 'PS5':'ps5', 'PS4':'ps4', 'XBS':'Xbox Series'}
    plataformasResultado = []
    for pla in plataformas:
        try:
            plataformasResultado.append(dictPlataformas[pla])
        except e:
            print(e)
    return plataformasResultado

# INICIAMOS SCRAPPING
url = 'https://as.com/meristation/juegos/lanzamientos/'
urlBase = 'https://as.com'

try:
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
    chromeOptions.add_experimental_option("prefs",prefs)
    chromeOptions.add_argument('--disable-blink-features=AutomationControlled')
    driver = webdriver.Chrome("chromedriver",options=chromeOptions)
    driver.get(url)
except Exception as e:
    print(e)

#esperamos aque carge la caja de los juegos
WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="mod-ga-det"')))
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()

divs = soup.find_all('div', class_='mod-ga-det')
cont = 0
juegos_lista = []
for i in range(0,150):
    nombre = divs[i].find('a', class_='l-und').text
    descripcion = divs[i].find('div', class_='ga-plot').text
    lis = divs[i].find_all('li', class_='rel-tags-i')
    plataformas = []
    for li in lis:
        plataformas.append(li.text)
    fecha_salida = divs[i].find('span', class_='ga-date').text
    urlJuego = urlBase + (divs[i].find('a', 'l-und'))['href']
    try:
        juego = cadaJuego(urlJuego, nombre, descripcion, plataformas, fecha_salida)
        juegos_lista.append(juego)
    except:
        print(nombre,'no se ha podido subir')

# SUBIMOS A FIREBASE
db = firebase.database()
db.child('proximos').remove()
for j in juegos_lista:
    db.child('proximos').push(j.__json__())