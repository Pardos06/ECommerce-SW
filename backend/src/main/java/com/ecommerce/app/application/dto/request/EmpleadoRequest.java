package com.ecommerce.app.application.dto.request;

public class EmpleadoRequest {
    private Integer id;
    private int usuarioId;
    private String area;
    private String cargo;

    public EmpleadoRequest() {
    }

    public EmpleadoRequest(Integer id, int usuarioId, String area, String cargo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.area = area;
        this.cargo = cargo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
