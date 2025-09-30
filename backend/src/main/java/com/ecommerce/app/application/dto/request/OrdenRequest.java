package com.ecommerce.app.application.dto.request;

import java.time.LocalDateTime;

public class OrdenRequest {
    private int id;
    private LocalDateTime fechaOrden;
    private String estado;
    private String estadoEmail;
    private int clienteId;
    private int metodoPagoId;

    public OrdenRequest() {
    }

    public OrdenRequest(int id, LocalDateTime fechaOrden, String estado, String estadoEmail, int clienteId, int metodoPagoId) {
        this.id = id;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.estadoEmail = estadoEmail;
        this.clienteId = clienteId;
        this.metodoPagoId = metodoPagoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDateTime fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoEmail() {
        return estadoEmail;
    }

    public void setEstadoEmail(String estadoEmail) {
        this.estadoEmail = estadoEmail;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(int metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }
}
