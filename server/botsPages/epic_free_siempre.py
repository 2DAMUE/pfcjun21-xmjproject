import urllib
from bs4 import BeautifulSoup
import pyrebase
import os
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait       
from selenium.webdriver.common.by import By       
from selenium.webdriver.support import expected_conditions as EC
from PIL import Image
from io import BytesIO
import requests
import time

url = 'https://www.epicgames.com/store/es-ES/browse?sortBy=releaseDate&sortDir=DESC&priceTier=tierFree&count=40&start=0'
try:
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
    chromeOptions.add_experimental_option("prefs",prefs)
    driver = webdriver.Chrome("chromedriver",options=chromeOptions)
    driver.get(url)
except Exception as e:
    print(e)


WebDriverWait(driver,20).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR,'img[class="css-1h7nwzt-Picture-styles__image-OfferCardImageArt__picture-OfferCardImagePortrait__picture-Picture-styles__visible"')))
time.sleep(5)
html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
soup = BeautifulSoup(html, "lxml")
driver.quit()

imgs = soup.find_all('img', class_="css-1h7nwzt-Picture-styles__image-OfferCardImageArt__picture-OfferCardImagePortrait__picture-Picture-styles__visible")

nombres = []
img_src = []
for i in range(0,len(imgs)):

    nombres.append(imgs[i]['alt'])
    print(nombres[i])
    img_src.append(imgs[i]['src'])
    print(img_src[i])