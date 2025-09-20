package com.ecommerce.app.application.dto.request;

public class CategoriaRequest {
    private int id;
    private String nombre;

    public CategoriaRequest() {
    }

    public CategoriaRequest(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
