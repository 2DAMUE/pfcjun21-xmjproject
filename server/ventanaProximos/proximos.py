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
storage = firebase.storage()
db = firebase.database()

class Juego():
    def __init__(self, nombre, descripcion, src_image, generos, fecha_salida, plataformas):
        self.nombre = nombre
        self.nombre_min = nombre.lower()
        self.descripcion = descripcion
        self.my_db_image = guardarImagen(src_image, nombre)
        self.generos = generos
        self.fecha_salida = fecha_salida
        self.plataformas = plataformas
        
    def __json__(self):
        return {'nombre': self.nombre, 'nombreMin': self.nombre_min ,'descripcion': self.descripcion, 'image_url': self.my_db_image,
         'generos': self.generos, 'fecha_salida': self.fecha_salida, 'plataformas': self.plataformas}


def guardarImagen(image_src, nombreJ):
    storage.child('proximos/'+nombreJ).put(image_src)
    url_my_db = storage.child('proximos/'+ nombreJ).get_url(None)
    return url_my_db

nombre = 'The Sisters: Party of the Year'
descripcion = '¡Que comiencen las travesuras!\n¡Acompaña a Marina, Wendy y todos sus amigos en esta nueva aventura en videojuego! ¡Enfréntate a minijuegos delirantes y recorre toda la ciudad de las Sisters!\n Wendy decide celebrar una fiesta de fin de año con sus amigos en casa. Antes de que pueda pedir permiso a sus padres, Marina, su insoportable hermana pequeña, tiene la misma idea y se le adelanta. Pero los padres solo autorizan una fiesta…\nPara saber quién la organizará, Marina y Wendy se lanzan una serie de desafíos, cada cuál más disparatado... Sister contra Sister... ¡Van a saltar chispas! '

srcImg = '/home/miguel/Pictures/' + 'The Sisters: Party of the Year.jpg'
generos = ['familiar', 'aventura']
fecha_salida = '16/06/2021'
plataformas = ['swicht', 'ps4']

juego = Juego(nombre, descripcion, srcImg, generos, fecha_salida, plataformas)

db.child('proximos').push(juego.__json__())
