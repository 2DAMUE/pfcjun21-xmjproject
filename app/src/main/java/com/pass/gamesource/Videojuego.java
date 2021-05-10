package com.pass.gamesource;

import java.util.Arrays;

public class Videojuego {
    private String titulo;
    private String precio;
    private String descripcion;
    private String uriFoto;
    private String uriEnlace;
    private String[] fecha;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Videojuego(String titulo, String precio, String descripcion, int img) {
        this.titulo = titulo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.img = img;
    }

    public Videojuego(String titulo, String precio, String descripcion, String uriFoto) {
        this.titulo = titulo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.uriFoto = uriFoto;
    }

    public String getUriEnlace() {
        return uriEnlace;
    }

    public void setUriEnlace(String uriEnlace) {
        this.uriEnlace = uriEnlace;
    }

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
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

    public String[] getFecha() {
        return fecha;
    }

    public void setFecha(String[] fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "titulo='" + titulo + '\'' +
                ", precio='" + precio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", uriFoto='" + uriFoto + '\'' +
                ", uriEnlace='" + uriEnlace + '\'' +
                ", fecha=" + Arrays.toString(fecha) +
                ", img=" + img +
                '}';
    }
}
