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
    def __init__(self, nombre, descripcion, my_db_image, generos, url_origen, plataformas):
        self.nombre = nombre
        self.descripcion = descripcion
        self.my_db_image = my_db_image
        self.generos = generos
        self.url_origen = url_origen
        self.plataformas = plataformas
    
    def __str__(self):
        return ("""el juego %s con fechas %s estado %s"""%(self.nombre, self.fecha , self.estado))

    def __json__(self):
        return {'nombre': self.nombre, 'descripcion': self.descripcion, 'image_url': self.my_db_image, 'generos': self.generos,
    'url_origen': self.url_origen ,'plataformas': self.plataformas}


################# FUNCIONES ###################
def sacar_datos(nombre, url_img, urlJuego):
    des_gen_plat = descripcion_generos(urlJuego)
    my_url_img = guardarImagen(url_img, nombre)
    return Juego(nombre, des_gen_plat[0], my_url_img, des_gen_plat[1], urlJuego, des_gen_plat[2])

def guardarImagen(image_url, nombreJ):
    image_object = requests.get(image_url)
    image = Image.open(BytesIO(image_object.content))
    image.save("img/xbox_free/" + nombreJ + "." + image.format, image.format)
    nombre_juego = nombreJ + "." + image.format
    storage.child('xbox_free/'+nombre_juego).put("img/xbox_free/" + nombre_juego)
    url_my_db = storage.child('xbox_free/'+ nombre_juego).get_url(None)
    return url_my_db


def descripcion_generos(urlJuego):
    #abrimos ventana con la pagina del principio pora obtener cuantas paginas de juegos hay
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
    #WebDriverWait(driver,20).until(EC.presence_of_element_located((By.ID,'div[id="titleGroup"')))
    WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="details"')))
    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')

    soup = BeautifulSoup(html, "lxml")
    driver.quit()
    div = soup.find('div', class_='details')
    descripcion = div.find('p').text.replace('\n', '')
    uls = soup.find_all('ul', class_='c-list f-bare f-lean')
    generos_text = (uls[3].find_all('li'))[1].text.replace('y ','')
    generos = generos_text.split(' ')
    plataformas_text = str(uls[4].find_all('li')[1]).replace('\xa0','')

    plataformas = []
    abierto = False
    actual = ''
    primera_vez = True
    for char in plataformas_text:
        if char == '<':
            abierto = True
            if primera_vez:
                primera_vez = False
            else:
                plataformas.append(actual)

        elif char == '>':
            abierto = False
            actual=''
        elif abierto == False:
            actual += char
    return [descripcion,generos,plataformas]


############ SCRAPPING #########################

url = 'https://www.xbox.com/es-ES/games/free-to-play'

#abrimos ventana con la pagina del principio pora obtener cuantas paginas de juegos hay
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
WebDriverWait(driver,40).until(EC.presence_of_element_located((By.CSS_SELECTOR,'h3[class="c-heading"')))
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()
aes = soup.find_all('a', class_='gameDivLink')
listaJuegos = []
for a in aes:
    url_juego = a['href']
    url_img = 'https:' + (a.find('img'))['src']
    nombreCaja = a.find('h3')
    nombre = nombreCaja.text
    listaJuegos.append(sacar_datos(nombre, url_img, url_juego))

# SUBIMOS A FIREBASE
db = firebase.database()
db.child('gratis').child('xbox_free').remove()
for j in listaJuegos:
    db.child("gratis").child("xbox_free").push(j.__json__())
