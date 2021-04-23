package com.pass.gamesource;

import android.net.Uri;


public class Videojuego {
    private String titulo;
    private String precio;
    private String descripcion;
    private String fecha;
    private int img;
    private Uri uri;

    public Videojuego() {
    }

    public Videojuego(String titulo, String descripcion, String fecha, int img) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
