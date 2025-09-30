package com.ecommerce.app.application.dto.request;

public class TipoProveedorRequest {
    private int id;
    private String nombre;

    public TipoProveedorRequest() {
    }

    public TipoProveedorRequest(int id, String nombre) {
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
