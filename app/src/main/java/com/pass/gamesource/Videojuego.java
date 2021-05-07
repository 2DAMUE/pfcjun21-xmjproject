package com.pass.gamesource;

public class Videojuego {
    private String titulo;
    private String precio;
    private String descripcion;
    private String uri;
    private String fecha;
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

    public Videojuego(String titulo, String precio, String descripcion, String uri) {
        this.titulo = titulo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
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

    @Override
    public String toString() {
        return "Videojuego{" +
                "titulo='" + titulo + '\'' +
                ", precio='" + precio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", URI='" + uri + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
