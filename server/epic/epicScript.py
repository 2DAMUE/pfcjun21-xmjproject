import urllib
from bs4 import BeautifulSoup
import pandas as pd
import zipfile
import os
from random import randint
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
import time

class Juego():
    def __init__(self, nombre, fecha, estado):
        self.nombre = nombre
        self.fecha = fecha
        self.estado = estado
    
    def __str__(self):
        return (""" el juego %s con fechas %s estado %s"""%(self.nombre, self.fecha , self.estado))


def sacar_datos(datos):
    estado = True
    if(datos[0].lower().find("gratis")):
        estado = False
    
    nombre = datos[1]
    if estado == True:
        fecha = [[-1, datos[2].split(' - ')[1]]]
    else:
        fecha = [datos[2].split(' - ')[0], datos[2].split(' - ')[1]]
    juego = Juego(nombre, fecha, estado)
    return juego



url = 'https://www.epicgames.com/store/es-ES/'

try:
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
    chromeOptions.add_experimental_option("prefs",prefs)
    driver = webdriver.Chrome("chromedriver",options=chromeOptions)
    driver.get(url)
except Exception as e:
    print(e)

WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="css-shu77l"')))
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()

divs = soup.find_all("div", class_="css-shu77l")

juegosLista = []
for div in divs:
    spans = div.find_all('span')
    datos_juego = []
    for span in spans:
        datos_juego.append(span.text)
    juegosLista.append(sacar_datos(datos_juego))

for j in juegosLista:
    print(j)
    print()

print('fin')


