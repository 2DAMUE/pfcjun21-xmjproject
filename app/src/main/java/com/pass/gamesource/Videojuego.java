package com.pass.gamesource;

import java.util.List;

public class Videojuego {
    private String nombre;
    private String descripcion;
    private String image_url;
    private String plataforma;
    private String url_origen;
    private List<String> fechas;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Videojuego(String nombre, String descripcion, String fecha, int img) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img = img;
    }

    public Videojuego(String nombre, String descripcion, String image_url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.image_url = image_url;
    }



    public String getUrl_origen() {
        return url_origen;
    }

    public void setUrl_origen(String url_origen) {
        this.url_origen = url_origen;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Videojuego(String nombre) {
        this.nombre = nombre;
    }

    public Videojuego() {
    }

    public Videojuego(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getFechas() {
        return fechas;
    }

    public void setFechas(List<String> fechas) {
        this.fechas = fechas;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", image_url='" + image_url + '\'' +
                ", plataforma='" + plataforma + '\'' +
                ", url_origen='" + url_origen + '\'' +
                ", fechas=" + fechas +
                ", img=" + img +
                '}';
    }
}
