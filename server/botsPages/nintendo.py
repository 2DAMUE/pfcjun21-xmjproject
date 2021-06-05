import urllib
from bs4 import BeautifulSoup
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
############ CREAMOS EL OBJETO JUEGO #################
class Juego():
    def __init__(self, nombre, descripcion, url_imagen, generos, url_origen):
        self.nombre = nombre
        self.nombre_min = nombre.lower()
        self.descripcion = descripcion
        self.my_db_image = guardarImagen(url_imagen, nombre)
        self.generos = generos
        self.plataforma = 'switch'
        self.url_origen = url_origen
    
    def __str__(self):
        return ("""el juego %s con fechas %s estado %s"""%(self.nombre, self.fecha , self.estado))

    def __json__(self):
        return {'nombre': self.nombre, 'nombreMin': self.nombre_min, 'descripcion': self.descripcion, 'image_url': self.my_db_image, 'generos': self.generos,
    'url_origen': self.url_origen ,'plataforma': self.plataforma}

# CREAMOS METODOS PARA EXTRAER DATOS
def guardarImagen(image_url, nombreJ):
    nombreJ = nombreJ.replace('/', '*').replace(' ','')
    image_object = requests.get(image_url)
    image = Image.open(BytesIO(image_object.content))
    image.save("img/nintendo/" + nombreJ + "." + image.format, image.format)
    nombre_juego = nombreJ + "." + image.format
    storage.child('nintendo/'+nombre_juego).put("img/nintendo/" + nombre_juego)
    url_my_db = storage.child('nintendo/'+ nombre_juego).get_url(None)
    return url_my_db

# INICIAMOS SCRAPING
seguir = True
primera = True
url = ''
cont = 1
juegos_lista = []
url_base = 'https://www.nintendo.es'
while seguir:
    if primera:
        url = 'https://www.nintendo.es/Buscar/Buscar-299117.html?f=147394-5-81-6952'
        primera = False
    else:
        url = 'https://www.nintendo.es/Buscar/Buscar-299117.html?f=147394-5-81-6952&p=' + str(cont) 
    try:
        chromeOptions = webdriver.ChromeOptions()
        chromeOptions.page_load_strategy = 'eager'
        prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
        chromeOptions.add_experimental_option("prefs",prefs)
        chromeOptions.add_argument('--disable-blink-features=AutomationControlled')
        driver = webdriver.Chrome("chromedriver",options=chromeOptions)
        driver.get(url)
    except Exception as e:
        print(e)
    try:
        driver.find_element_by_link_text("Permitir cookies y cerrar este mensaje").click()
        WebDriverWait(driver,20).until(EC.presence_of_element_located((By.TAG_NAME,'div[class="search-result-img col-xs-3 col-sm-2"')))
    except:
        driver.quit()
        seguir = False
    if seguir:
        cont += 1
        time.sleep(2)
        body =  driver.execute_script("return document.body")
        html = body.get_attribute('innerHTML') 
        soup = BeautifulSoup(html, "html5lib")
        driver.quit()
        divs = soup.find_all('div', class_='search-result-img col-xs-3 col-sm-2')
        divs2 = soup.find_all('div', class_='search-result-txt col-xs-9 col-sm-10')
        div3 = soup.find('div', class_='col-xs-12 col-sm-9 col-lg-9')
        aes = div3.find_all('a')
        spans = div3.find_all('span', class_='hidden-xs hidden-sm')
        #print('creo juegos')
        for i in range(1,len(divs)):
            nombre = (divs[i].find('img'))['alt']
            img_url = (divs[i].find('img'))['src']
            descripcion = divs2[i].find('p', class_='hidden-xs visible-sm visible-md visible-lg').text
            url_juego = url_base + aes[i]['href']
            generospan = spans[i].find_all('span')
            generos = []
            for i in range(1,len(generospan)):
                if(len(generospan[i].text.replace(',', '')) > 1):
                    generos.append(generospan[i].text.replace(',', '').strip())
            #print('creo juego', nombre)
            juegos_lista.append(Juego(nombre, descripcion, img_url, generos, url_juego))

# SUBIMOS A FIREBASE
db = firebase.database()
db.child('gratis').child('nintendo').remove()
for j in juegos_lista:
    db.child("gratis").child("nintendo").push(j.__json__())