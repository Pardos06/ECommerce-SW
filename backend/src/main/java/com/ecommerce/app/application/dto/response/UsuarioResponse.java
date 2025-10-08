package com.ecommerce.app.application.dto.response;

public class UsuarioResponse {
    private int id;
    private String nombre;
    private String email;
    private String estado;
    private String rol;
    private Integer clienteId;
    private Integer empleadoId;

    public UsuarioResponse() {
    }

    public UsuarioResponse(int id, String nombre, String email, String estado, String rol, Integer clienteId, Integer empleadoId) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.estado = estado;
        this.rol = rol;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }
}
