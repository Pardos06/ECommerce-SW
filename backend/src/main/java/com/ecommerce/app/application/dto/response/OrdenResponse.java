package com.ecommerce.app.application.dto.response;

public class OrdenResponse {
    private int id;
    private String estado;
    private String estadoEmail;
    private String cliente;
    private String metodoPago;
    private int clienteId;
    private int metodoPagoId;

    public OrdenResponse() {
    }

    public OrdenResponse(int id, String estado, String estadoEmail, String cliente, String metodoPago, int clienteId, int metodoPagoId) {
        this.id = id;
        this.estado = estado;
        this.estadoEmail = estadoEmail;
        this.cliente = cliente;
        this.metodoPago = metodoPago;
        this.clienteId = clienteId;
        this.metodoPagoId = metodoPagoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
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
