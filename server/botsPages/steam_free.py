import urllib
from bs4 import BeautifulSoup
import pyrebase
import os
import time
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
from PIL import Image
from io import BytesIO
import requests
from random import randint

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

# SACAMOS LOS ENLACES DE TODOS LOS JUEGOS GRATIS DE STEAM
url = 'https://store.steampowered.com/genre/Free%20to%20Play/#p=0&tab=NewReleases'

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
WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="tabarea"')))
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()
#sacamos la caja
div = soup.find('div', id='NewReleasesTable')
#sacamos el nuemo de paginas
spans = div.find_all('span', class_='paged_items_paging_pagelink')
#creamos i¡un entero con el numero de paginas que tenemos que mirar
last_page = int(spans[len(spans)-1].text)
#array qure guarda las url de los juegos a observar
urls_games = []
#Sacamos las urls de los distintos juego gratuitos, damos tantas vueltas como paginas tenemos que analizar
for i in range(0, last_page):
    #iteramos la url por las distintas paginas con tiempo aleatorio de espera para evitar la deteccion
    url = 'https://store.steampowered.com/genre/Free%20to%20Play/#p='+str(i)+'&tab=NewReleases'
    time.sleep(randint(2,7))
    try:
        chromeOptions = webdriver.ChromeOptions()
        prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
        chromeOptions.add_experimental_option("prefs",prefs)
        chromeOptions.add_argument('--disable-blink-features=AutomationControlled')
        driver = webdriver.Chrome("chromedriver",options=chromeOptions)
        driver.get(url)
    except Exception as e:
        print(e)

    #esperamsoa que cargue la caja de los juegos
    WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="tabarea"')))

    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')

    soup = BeautifulSoup(html, "lxml")
    driver.quit()
    #sacamos la caja de juegos con su id
    div = soup.find('div', id='NewReleasesTable')
    #sacamos topdas las etiquetas a de la caja, cada una corresponde a un juego
    aes = div.find_all('a', class_='tab_item')
    #Introducimos las urls de los atributos href de las etiquetas a, corresponden a la url de cada juego que queremos examinar
    for a in aes:
        urls_games.append(a['href'])
# Hasta aqui hemos obtenido los enlaces de los juegos que queriamos
# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
# CREAMOS LA CLASE JUEGO PARA INTRODUCIR LOS DATOS
class Juego():
    def __init__(self, nombre, descripcion, my_db_image, generos, url_origen):
        self.nombre = nombre
        self.nombre_min = nombre.lower()
        self.descripcion = descripcion
        self.my_db_image = my_db_image
        self.generos = generos
        self.url_origen = url_origen
        self.plataforma = 'pc'
        
    def __json__(self):
        return {'nombre': self.nombre, 'nombreMin': self.nombre_min, 'descripcion': self.descripcion, 'image_url': self.my_db_image,
         'generos': self.generos, 'url_origen': self.url_origen, 'plataforma': self.plataforma}

# CREAMOS METODOS PARA EXTRAER DATOS
def guardarImagen(image_url, nombreJ):
    nombreJ = nombreJ.replace('/', '*').replace(' ', '')
    image_object = requests.get(image_url)
    image = Image.open(BytesIO(image_object.content))
    image.save("img/steam_free/" + nombreJ + "." + image.format, image.format)
    nombre_juego = nombreJ + "." + image.format
    storage.child('steam_free/'+nombre_juego).put("img/steam_free/" + nombre_juego)
    url_my_db = storage.child('steam_free/'+ nombre_juego).get_url(None)
    return url_my_db

# BAJAMOS DATOS DE CADA JUEGO
juegos = []
for urlJ in urls_games:
    html = urllib.request.urlopen(urlJ)
    soup = BeautifulSoup(html, "lxml")
    #driver.quit()
    # OBTENEMOS NOMBRE
    nombre = (soup.find('div', class_='apphub_AppName')).text
    # OBTENEMOS DESCRIPCION
    descripcion = (soup.find('div', class_='game_description_snippet')).text.replace('\r','').replace('\t','').replace('\n','')
    # OBTENEMOS GENEROS
    aes = soup.find_all('a', class_='app_tag')
    generos = []
    for a in aes:
        generos.append((a.text).replace('\r','').replace('\t','').replace('\n',''))
    # OBTENEMOS URL IMAGEN
    url_img = soup.find('img', class_='game_header_image_full')['src']
    # FALTA SUBIR IMAGEN A STORAGE Y BAJARLE LA URL
    my_url_img = guardarImagen(url_img, nombre)
    j = Juego(nombre, descripcion, my_url_img, generos, urlJ)
    juegos.append(j)
    #FIN DEL BUCLE QUE BUSCA EN CADA URL DE CADA JUEGO


# SUBIMOS A FIREBASE

db = firebase.database()
db.child('gratis').child('steam_free').remove()
for j in juegos:
    db.child("gratis").child("steam_free").push(j.__json__())

