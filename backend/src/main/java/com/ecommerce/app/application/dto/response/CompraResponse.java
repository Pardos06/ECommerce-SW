package com.ecommerce.app.application.dto.response;

import java.time.LocalDateTime;

public class CompraResponse {
    private int id;
    private LocalDateTime fechaCompra;
    private String estado;
    private String proveedor;
    private String empleado;

    public CompraResponse() {
    }

    public CompraResponse(int id, LocalDateTime fechaCompra, String estado, String proveedor, String empleado) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.proveedor = proveedor;
        this.empleado = empleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
}
