package com.pass.gamesource;

import android.net.Uri;

public class Videojuego {
    private String titulo;
    private String precio;
    private String descripcion;
    private Uri uri;

    public Videojuego(String titulo, String precio, String descripcion, Uri uri) {
        this.titulo = titulo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Videojuego(String titulo) {
        this.titulo = titulo;
    }

    public Videojuego() {
    }

    public Videojuego(String titulo, String precio, String descripcion) {
        this.titulo = titulo;
        this.precio = precio;
        this.descripcion = descripcion;
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
}
