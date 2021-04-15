import urllib
from bs4 import BeautifulSoup
import zipfile
import os
from random import randint
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
import time

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
         self.precio_juegos = {}

    def __str__(self):
        return (""" el bundle %s con un rango de precios %s"""%(self.nombre, self.precio_juegos))

         

####################################################
## De esta funcion obtenemos el objeto paquete de juegos de la pagina humble bundle
def obtenerDatosPaquete(nombre,url):
    paquete = Paquete(nombre)
    html = urllib.request.urlopen(url)
    soup = BeautifulSoup(html, "lxml")

    divs = soup.find_all('div', class_= 'main-content-row dd-game-row js-nav-row')

    for div in divs:
        minPrice = div.find('h2')
        precio = sacarPrecio(minPrice.text)
        spans = div.find_all('span', class_='front-page-art-image-text')
        juegos = []
        for span in spans:
            juegos.append(span.text)
        
        paquete.precio_juegos[precio] = juegos
    return paquete
######################################################################################

# De esta funcion obtenemos solo el valor del precio por tramos de los paquetes
def sacarPrecio(linea):
    precio = ""
    palabras = linea.split(' ')
    for palabra in palabras:
        #print(palabra, type(palabra))
        if(palabra[0:1] == "€"):
            precio = palabra
            break

    
    return precio
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

for p in paquetes_section:
    print(p)
    print('- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -')


    

