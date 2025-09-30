package com.ecommerce.app.application.dto.request;

import java.time.LocalDateTime;

public class CompraRequest {
    private int id;
    private LocalDateTime fechaCompra;
    private String estado;
    private int metodoPagoId;
    private int proveedorId;
    public int empleadoId;

    public CompraRequest() {
    }

    public CompraRequest(int id, LocalDateTime fechaCompra, String estado, int metodoPagoId, int proveedorId, int empleadoId) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.metodoPagoId = metodoPagoId;
        this.proveedorId = proveedorId;
        this.empleadoId = empleadoId;
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

    public int getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(int metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }
}
