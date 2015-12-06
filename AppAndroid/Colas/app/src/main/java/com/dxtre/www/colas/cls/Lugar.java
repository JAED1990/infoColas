package com.dxtre.www.colas.cls;

public class Lugar {
    private int idlugar;
    private String lat;
    private String lng;
    private String nombre;
    private String imagen;
    private String id_categoria;

    public Lugar(int idlugar, String lat, String lng, String nombre, String imagen, String id_categoria) {
        this.idlugar = idlugar;
        this.lat = lat;
        this.lng = lng;
        this.nombre = nombre;
        this.imagen = imagen;
        this.id_categoria = id_categoria;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getIdlugar() {
        return idlugar;
    }

    public void setIdlugar(int idlugar) {
        this.idlugar = idlugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
