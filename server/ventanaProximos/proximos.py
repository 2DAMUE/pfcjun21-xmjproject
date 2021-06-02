import tkinter 
import tkinter as tk
from tkinter import *
from tkinter import filedialog
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

class Juego():
    def __init__(self, nombre, descripcion, src_image, generos, fecha_salida, plataformas):
        self.nombre = nombre
        self.descripcion = descripcion
        self.my_db_image = guardarImagen(src_image, nombre)
        self.generos = generos
        self.fecha_salida = fecha_salida
        self.plataformas = plataformas
        
    def __json__(self):
        return {'nombre': self.nombre, 'descripcion': self.descripcion, 'image_url': self.my_db_image,
         'generos': self.generos, 'fecha_salida': self.fecha_salida, 'plataformas': self.plataformas}

def guardarImagen(image_src, nombreJ):
    print(image_src)
    storage.child('proximos/'+nombreJ).put(image_src)
    url_my_db = storage.child('proximos/'+ nombreJ).get_url(None)
    return url_my_db

'''
estos metodo aceptan el meter en la listbox de generos el genero
del entry de genero, uno funciona para el bopton y otro al pulsar intro per solo si tiene 2
o mas caracteres
'''
#al pulsar intro comprueba ambos caso, genero y plataformas
generosGuardados = []
plataformasGuardadas = []
def introKey(event):
    if genero.get() not in generosGuardados and len(genero.get()) > 1:
        Lbgeneros.insert(tk.END, genero.get())
        generosGuardados.append(genero.get())
        genero.delete(0,'end')
    if plataforma.get() not in plataformasGuardadas and len(plataforma.get()) > 1:
        Lbplataformas.insert(tk.END, plataforma.get())
        plataformasGuardadas.append(plataforma.get())
        plataforma.delete(0,'end')
def introGeneroBtn():
    if genero.get() not in generosGuardados and len(genero.get()) > 1:
        Lbgeneros.insert(tk.END, genero.get())
        generosGuardados.append(genero.get())
        genero.delete(0,'end')
#Lo mismo con plataformas 
def introPlataformaBtn():
    if plataforma.get() not in plataformasGuardadas and len(plataforma.get()) > 1:
        Lbplataformas.insert(tk.END, plataforma.get())
        plataformasGuardadas.append(plataforma.get())
        plataforma.delete(0,'end')
#funcion que elige, guara y escribe en pantalla el src de la imagen a subir
src = 'buscar imagen'
def chooseDir():
    src =  filedialog.askopenfilename(initialdir = "/home/miguel/Pictures",
                                          title = "Select a File",
                                          filetypes = (("jpeg files","*.jpeg*"),
                                                       ("png files", "*.png*"),
                                                       ("jpg files", "*.jpg*"),
                                                       ("all files", "*.*")))
    srcLabel2 = Label(ventana, text = src, bg='black', fg='orange', font='HELVETICA 8').place(x =650 , y = 250)
    
def subirJuego():
    nom = nombre.get()
    desc = descripcion.get('1.0', END)
    gens = Lbgeneros.get(1,END)
    fech = fecha.get()
    plats = Lbplataformas.get(1,END)
    juego = Juego(nom, desc, src, gens, fech, plats)
    print(juego.__json__())
#VENATANA
ventana = tkinter.Tk()
ventana.title("GAME SOURCE")
ventana.config(bg="white")
ventana.geometry("1200x800")

Label(ventana, text="introducir proximos estrenos", bg='black', fg='orange', cursor="dot", font='HELVETICA',
             justify='center' ,height=2, width=500).pack()

Label(ventana, text="NOMBRE:", bg='white', fg='black',cursor="dot", font='HELVETICA 8',
             justify='center').place(x =50 , y = 80)
nombre =Entry(ventana, fg='orange', bg='black', width=20, justify=LEFT)
nombre.place(x =50 , y = 100)

Label(ventana, text="DESCRIPCION:", cursor="dot", font='HELVETICA 8',
             justify='center', bg='white', fg='black').place(x =50 , y = 160)
descripcion = tk.Text(ventana, height=4, width=30, fg='orange', bg='black')
descripcion.place(x =50 , y = 200)

Label(ventana, text="GENEROS:", bg='white', fg='black',cursor="dot", font='HELVETICA 8',
             justify='center').place(x =50 , y = 350)
genero = Entry(ventana, fg='orange', bg='black', width=20, justify=LEFT)
genero.place(x =50 , y = 380)

Lbgeneros = Listbox(ventana, bg='gray21', fg='orange',selectmode=MULTIPLE)
Lbgeneros.place(x = 50, y = 420)
anandir_gen = Button(ventana, text='Añadir',bg='black', fg='orange', command=introGeneroBtn ).place(x =300 , y =380)


Label(ventana, text="FECHA- DD/MM/YYYY:", bg='white', fg='black',cursor="dot", font='HELVETICA 8',
             justify='center').place(x =750 , y = 80)
fecha = Entry(ventana, fg='orange', bg='black', width=20, justify=LEFT)
fecha.place(x =750 , y = 100)



srcLabel = Label(ventana, text = src, bg='black', fg='orange', font='HELVETICA 8').place(x =750 , y = 250)
b_chooseFile = tkinter.Button(ventana, text = "Buscar imagen",bg = 'black', fg = 'orange', command = chooseDir)
b_chooseFile.place(x = 950,y = 250)

Label(ventana, text="PLATAFORMAS:", bg='white', fg='black',cursor="dot", font='HELVETICA 8',
             justify='center').place(x =750 , y = 350)
plataforma = Entry(ventana, fg='orange', bg='black', width=20, justify=LEFT)
plataforma.place(x =750 , y = 380)

Lbplataformas = Listbox(ventana, bg='gray21', fg='orange',selectmode=MULTIPLE)
Lbplataformas.place(x = 750, y = 420)
anandir_pla = Button(ventana, text='Añadir',bg='black', fg='orange', command=introPlataformaBtn ).place(x =1000 , y =380)
ventana.bind('<Return>', introKey)

subir_juego = Button(ventana, text='subir',bg='black', fg='orange', command=subirJuego).place(x =500 , y =580)


ventana.mainloop()