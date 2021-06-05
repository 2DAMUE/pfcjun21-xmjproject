import urllib
from bs4 import BeautifulSoup
import os
from random import randint
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
import pyrebase
from datetime import datetime
from datetime import timedelta
import time

###### valores de los section de la primera url para distinguir paquetes########
# Juegos = 0
# Libros = 1
# Sofware = 2
valor_section = 0
#################################################################################
config = {
    "apiKey": "AIzaSyAsxaPfthuvrzCp2DCL0Sz4Fs1XzDtd7hg",
    "authDomain": "gamesource-9bc51.firebaseapp.com",
    "databaseURL": "https://gamesource-9bc51-default-rtdb.europe-west1.firebasedatabase.app",
    "projectId": "gamesource-9bc51",
    "storageBucket": "gamesource-9bc51.appspot.com"
    }

firebase = pyrebase.initialize_app(config)
############### clase de juegos ####################
class Paquete():
    def __init__(self, nombre, url_origen):
         self.nombre = nombre
         self.nombre_min = nombre.lower()
         self.precio_tramos = []
         self.juegos_por_tramo = []
         self.fecha_expiracion = ''
         self.url_origen = url_origen

    def __str__(self):
        return (""" el bundle %s con un rango de precios %s para los juegos %s"""%(self.nombre, self.precio_tramos, self.juegos_por_tramo))

    def __json__(self):
        return {'nombre':self.nombre,'nombreMin': self.nombre_min ,'precio_tramos': self.precio_tramos, 'juegos_tramo': self.juegos_por_tramo ,
         'fecha_expiracion': self.fecha_expiracion, 'url_origen': self.url_origen }

####################################################
## De esta funcion obtenemos el objeto paquete de juegos de la pagina humble bundle
def obtenerDatosPaquete(nombre,url):
    paquete = Paquete(nombre, url)
    html = urllib.request.urlopen(url)
    soup = BeautifulSoup(html, 'lxml')
    divs = soup.find_all('div', class_= 'main-content-row dd-game-row js-nav-row')
    
    try:
        chromeOptions = webdriver.ChromeOptions()
        prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
        chromeOptions.add_experimental_option("prefs",prefs)
        driver = webdriver.Chrome("chromedriver",options=chromeOptions)
        driver.get(url)
    except Exception as  e:
        print(e)

    WebDriverWait(driver, 20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="countdown-timer-number js-countdown-timer-number"')))
    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
    soup = BeautifulSoup(html, 'lxml')
    driver.quit()

    divs = soup.find_all('div', class_= 'main-content-row dd-game-row js-nav-row')
    #### FECHA ####
    fecha = sacarFecha(soup.find_all('div', class_='countdown-timer-number js-countdown-timer-number'))
    paquete.fecha_expiracion = fecha
    ######################
    for div in divs:
        #### PRECIO #####
        #print(div)
        minPrice = div.find('h2')
        precio = sacarPrecio(minPrice.text)
        paquete.precio_tramos.append(precio)
        #####################3
        ###### LISTA JUEGOS #########
        spans = div.find_all('span', class_='front-page-art-image-text')
        juegos = []
        for span in spans:
            juegos.append(span.text)
        ###############################
        paquete.juegos_por_tramo.append(juegos)
    
    return paquete
######################################################################################

# De esta funcion obtenemos solo el valor del precio por tramos de los paquetes
def sacarPrecio(linea):
    #print(linea)
    precio = ""
    palabras = linea.split(' ')
    print('*',palabras)
    for palabra in palabras:
        if '€' in palabra:
            precio = palabra.replace('€','').replace('\xa0','')
            break
    return precio

def sacarFecha(divsFecha):
    today = datetime.now()
    #today + divs[0].text
    date = str(today + timedelta(days=int(divsFecha[0].text)))
    return date.split(' ')[0]

########################################################################################
url_base = 'https://www.humblebundle.com'
url = 'https://www.humblebundle.com/bundles?hmb_source=navbar'

try:
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
    chromeOptions.add_experimental_option("prefs",prefs)
    driver = webdriver.Chrome("chromedriver",options=chromeOptions)
    driver.get(url)
except Exception as e:
    print(e)

WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="mosaic-view"')))
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()

div = soup.find('div', class_='landing-mosaic-section light-bg')
h3s = div.find_all('h3')
sections = div.find_all('section', class_='landing-page-mosaic')
if len(h3s) == len(sections): 
    paquetes_section = []
    aes = sections[valor_section].find_all('a')
    nombres = sections[valor_section].find_all('span', class_='name')
    for i in range(0, len(aes)):
        url_bundle = url_base + aes[i].attrs['href']
        nombre = nombres[i].text
        time.sleep(randint(1,3))
        paquetes_section.append(obtenerDatosPaquete(nombre, url_bundle))


    db = firebase.database()
    db.child('humble').remove()
    for p in paquetes_section:
        juego = p.__json__()
        #print(juego)
        db.child("humble").push(juego)
    # CAMNBIAR OBJETO PAQUETE PARA ADAPTARSE AL JSON DE FIREBASE
else:
    db = firebase.database()
    db.child('humble').remove() 

