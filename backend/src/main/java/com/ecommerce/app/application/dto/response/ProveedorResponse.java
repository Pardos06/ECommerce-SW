package com.ecommerce.app.application.dto.response;

public class ProveedorResponse {
    private int id;
    private String nombre;
    private int telefono;
    private String email;
    private String direccion;
    private int tipoProveedorId;

    public ProveedorResponse() {
    }

    public ProveedorResponse(int id, String nombre, int telefono, String email, String direccion, int tipoProveedorId) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.tipoProveedorId = tipoProveedorId;
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTipoProveedorId() {
        return tipoProveedorId;
    }

    public void setTipoProveedorId(int tipoProveedorId) {
        this.tipoProveedorId = tipoProveedorId;
    }
}
