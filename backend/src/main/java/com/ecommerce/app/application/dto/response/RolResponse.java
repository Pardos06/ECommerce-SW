package com.ecommerce.app.application.dto.response;

public class RolResponse {
    private int id;
    private String nombre;

    public RolResponse() {
    }

    public RolResponse(int id, String nombre) {
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
