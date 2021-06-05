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
    def __init__(self, nombre, descripcion, my_db_image, generos, url_origen):
        self.nombre = nombre
        self.nombre_min = nombre.lower()
        self.descripcion = descripcion
        self.my_db_image = my_db_image
        self.generos = generos
        self.plataforma = 'ps'
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
    image.save("img/ps_store_free/" + nombreJ + "." + image.format, image.format)
    nombre_juego = nombreJ + "." + image.format
    storage.child('ps_store_free/'+nombre_juego).put("img/ps_store_free/" + nombre_juego)
    url_my_db = storage.child('ps_store_free/'+ nombre_juego).get_url(None)
    return url_my_db

def sacar_datos(nombre, url_img, urlJuego):
    des_gen = descripcion_generos(urlJuego)
    my_url_img = guardarImagen(url_img, nombre)
    return Juego(nombre, des_gen[0], my_url_img, des_gen[1], urlJuego)

def descripcion_generos(urlJuego):
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
    WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="psw-bg-0 psw-body-2"')))
    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
    soup = BeautifulSoup(html, "lxml")
    driver.quit()
    divsDescripcion = soup.find_all('div', class_='psw-bg-0 psw-body-2')
    try:
        descripcion = divsDescripcion[2].text
    except:
        descripcion = divsDescripcion[0].text
    divsGeneros = soup.find('div', class_='psw-cell psw-mobile-p-12 psw-tablet-l-5 psw-m-b-l psw-m-b-0@tablet-l')
    try:
        generos = (divsGeneros.find('span')).text.split(', ')
    except:
        generos = []
    return (descripcion,generos)

# INICIAMOS EL RECORRIDO DE CADA PAGINA DE PS STORE
url = 'https://store.playstation.com/es-es/category/5c30b111-b867-4037-8f42-5b3db18d8e20/1'
urlBase = 'https://store.playstation.com'
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
WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="ems-sdk-grid-paginator__page-buttons"')))
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()
div = soup.find('div', class_='ems-sdk-grid-paginator__page-buttons')
buttons = div.find_all('button')
fin = int(buttons[len(buttons)-1].text)+1
cont = 1
juegos_lista = []
while fin != cont:
    print(cont)
    # INICIAMOS SCRAPING
    #abrimos ventana con la pagina del principio pora obtener cuantas paginas de juegos hay
    url = 'https://store.playstation.com/es-es/category/5c30b111-b867-4037-8f42-5b3db18d8e20/'+str(cont)
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
    WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'li[class="psw-cell"')))
    time.sleep(2)
    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
    soup = BeautifulSoup(html, "lxml")
    driver.quit()
    lis = soup.find_all('li', class_='psw-cell')
    for li in lis:
        #Nombre del juego
        nombre = li.find('span', class_='psw-body-2 psw-truncate-text-2 psw-p-t-2xs').text
        #Estado del juego, si no por 'Gratis no nos interesa
        estado = li.find('span', class_='price').text
        #url de la imagen
        img = li.find('img', class_='psw-fade-in psw-top-left psw-l-fit-cover')
        url_img = img['src']
        #url a la pagina del juego
        urlJuego = urlBase + (li.find('a'))['href']
        if estado.lower() == 'gratis':
            juegos_lista.append(sacar_datos(nombre, url_img, urlJuego))
    cont += 1

# SUBIMOS A FIREBASE
db = firebase.database()
db.child('gratis').child('ps_store_free').remove()
for j in juegos_lista:
    db.child("gratis").child("ps_store_free").push(j.__json__())
