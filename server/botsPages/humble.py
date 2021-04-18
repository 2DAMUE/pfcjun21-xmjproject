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

###### valores de los section de la primera url para distinguir paquetes########
# Juegos = 0
# Libros = 1
# Sofware = 2
valor_section = 0
#################################################################################

############### clase de juegos ####################
class Paquete():
    def __init__(self, nombre):
         self.nombre = nombre
         self.precio_tramos = []
         self.juegos_por_tramo = []
         self.fecha_expiracion = ''

    def __str__(self):
        return (""" el bundle %s con un rango de precios %s para los juegos %s"""%(self.nombre, self.precio_tramos, self.juegos_por_tramo))

         

####################################################
## De esta funcion obtenemos el objeto paquete de juegos de la pagina humble bundle
def obtenerDatosPaquete(nombre,url):
    paquete = Paquete(nombre)
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

    WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="countdown-timer-number js-countdown-timer-number"')))
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

sections = soup.find_all('section', class_='landing-page-mosaic')

paquetes_section = []
aes = sections[valor_section].find_all('a')
nombres = sections[valor_section].find_all('span', class_='name')
for i in range(0, len(aes)):
    url_bundle = url_base + aes[i].attrs['href']
    nombre = nombres[i].text
    paquetes_section.append(obtenerDatosPaquete(nombre, url_bundle))


config = {
    "apiKey": "AIzaSyAsxaPfthuvrzCp2DCL0Sz4Fs1XzDtd7hg",
    "authDomain": "gamesource-9bc51.firebaseapp.com",
    "databaseURL": "https://gamesource-9bc51-default-rtdb.europe-west1.firebasedatabase.app",
    "projectId": "gamesource-9bc51",
    "storageBucket": "gamesource-9bc51.appspot.com"
    '''
    # estan comentado por que da error en principio no me pide estos datos, 
    # los comento para guardarlos a futuro
    "messagingSenderId": "204253438311",
    "appId": "1:204253438311:web:fb99cf095a8bee96b10fc2",
    "measurementId": "G-NK36BG5MBP"
    '''
}

firebase = pyrebase.initialize_app(config)

db = firebase.database()
db.child('humble').remove()
for p in paquetes_section:
    juego = {'nombre':p.nombre, 'precio_tramos': p.precio_tramos, 'juegos_tramo': p.juegos_por_tramo , 'fecha_expiracion': p.fecha_expiracion }
    #print(juego)
    db.child("humble").push(juego)
# CAMNBIAR OBJETO PAQUETE PARA ADAPTARSE AL JSON DE FIREBASE
    

