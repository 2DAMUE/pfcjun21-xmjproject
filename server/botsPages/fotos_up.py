import pyrebase

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
db = firebase.database()

epic_games = db.child('gratis').child('epic').get()
print(epic_games)
#storage = firebase.storage()

#storage.download('epic/foto2.jpeg')
#url = storage.child('epic/foto2.jpeg').get_url(None)
#####Por lo que sea esto ya funciona
#storage.child('epic/foto3.JPEG').put('img/epic/foto3.JPEG')
###########################################################
#url = storage.child('epic/foto3.JPEG').get_url(None)
#print(url)
print('fin')