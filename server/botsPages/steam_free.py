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

url = 'https://store.steampowered.com/genre/Free%20to%20Play/#p=0&tab=NewReleases'


try:
    chromeOptions = webdriver.ChromeOptions()
    prefs = {"download.default_directory" : os.getcwd()+"/Resultados Selenium"}
    chromeOptions.add_experimental_option("prefs",prefs)
    driver = webdriver.Chrome("chromedriver",options=chromeOptions)
    driver.get(url)
except Exception as e:
    print(e)


WebDriverWait(driver,20).until(EC.presence_of_element_located((By.CSS_SELECTOR,'div[class="tabarea"')))

html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')

soup = BeautifulSoup(html, "lxml")


div = soup.find('div', id='NewReleasesTable')
spans = div.find_all('span', class_='paged_items_paging_pagelink')
last_page = int(spans[len(spans)-1].text)
urls_games = []
for i in range(0, last_page):
    print(i)
    html = driver.find_element_by_xpath("//body").get_attribute('outerHTML')
    soup = BeautifulSoup(html, "lxml")
    div = soup.find('div', id='NewReleasesTable')
    spans = div.find_all('span', class_='paged_items_paging_pagelink')
    aes = div.find_all('a', class_='tab_item')
    for a in aes:
        urls_games.append(a['href'])
    time.sleep(randint(5,15))
    driver.find_element_by_css_selector('span[id="NewReleases_btn_next" ]').click()
    

driver.quit()

for url in urls_games:
    print(url)

print(len(urls_games))