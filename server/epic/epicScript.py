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


url = 'https://www.epicgames.com/store/es-ES/'
#html = urllib.request.urlopen(url)
#soup = BeautifulSoup(html)
try:
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
    chromeOptions.add_experimental_option("prefs",prefs)
    driver = webdriver.Chrome("chromedriver",options=chromeOptions)
    driver.get(url)
except Exception as e:
    print(e)

for i in range(0,4):
    try:
        #Esta orden hace que la pagina espera a que se carge el elemento a buscar
        #Es decir espera a que carge la caja de juegos gratuitos
        WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div [class="css-shu77l"')))
        #esto espera un numero aleatorio de segundos para simular no ser una autamatizacion
        time.sleep(randint(2,7))
        #clase de la caja para seleccionarla, puede cambiar segun las actualizaciones
        clase_caja_juegos = "css-shu77l"
        #selecciona la caja a clikar y clica en ella
        driver.find_elements_by_css_selector('div[class='+clase_caja_juegos+' ]')[i].click()
        #cambia a la nueva ventana, y tiene el bloque try-except por que salta un error siempre, pero funciona
        try:
            driver.switch_to().window(driver.window_handles[1])
        except Exception as e:
            print()
        
        #guardo en una string la url de la nueva ventana
        actualurl = str(driver.current_url)

    except:
        print('error')
    #vuelve a la ventana anterior
    driver.back()
    #obtengo nombre del juego de la url de la ventana
    juego_gratis_actual_array = actualurl.split('/')
    juego_gratis_actual = juego_gratis_actual_array[len(juego_gratis_actual_array)-1].replace('-',' ')
    #imprimo los datos en cuestion
    print('juego', i, ':' ,juego_gratis_actual)
    pass

#Cierro el navegador
driver.quit()


