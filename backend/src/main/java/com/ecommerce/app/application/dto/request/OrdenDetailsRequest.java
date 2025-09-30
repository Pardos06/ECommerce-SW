package com.ecommerce.app.application.dto.request;

import java.math.BigDecimal;

public class OrdenDetailsRequest {
    private int id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private int ordenId;
    private int productoId;

    public OrdenDetailsRequest() {
    }

    public OrdenDetailsRequest(int id, int cantidad, BigDecimal precioUnitario, int ordenId, int productoId) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.ordenId = ordenId;
        this.productoId = productoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(int ordenId) {
        this.ordenId = ordenId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}
