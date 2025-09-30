package com.ecommerce.app.application.dto.request;

public class ClienteRequest {
    private int id;
    private int usuarioId;
    private String telefono;
    private String direccion;

    public ClienteRequest() {
    }

    public ClienteRequest(int id, int usuarioId, String telefono, String direccion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
