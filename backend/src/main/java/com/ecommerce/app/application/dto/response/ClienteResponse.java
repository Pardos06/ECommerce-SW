package com.ecommerce.app.application.dto.response;

public class ClienteResponse {
    private int id;
    private String telefono;
    private String direccion;
    private String usuario;
    private String email;

    public ClienteResponse() {
    }

    public ClienteResponse(int id, String telefono, String direccion, String usuario, String email) {
        this.id = id;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuario = usuario;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
