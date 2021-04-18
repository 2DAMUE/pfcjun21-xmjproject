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
import json

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
        fecha = [-1, datos[2].split(' - ')[1]]
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


########## A partir de aqui subimos los datos a firebase #############
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
db.child('gratis').child('epic').remove()
for j in juegosLista:
    juego = {'nombre':j.nombre, 'fechas': [j.fecha[0],j.fecha[1]], 'estado': j.estado }
    db.child("gratis").child("epic").push(juego)
