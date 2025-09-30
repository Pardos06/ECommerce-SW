package com.ecommerce.app.application.dto.response;

import java.math.BigDecimal;

public class OrdenDetailsResponse {
    private int id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private int ordenId;
    private int productoId;
    private String producto;

    public OrdenDetailsResponse() {
    }

    public OrdenDetailsResponse(int id, int cantidad, BigDecimal precioUnitario, int ordenId, int productoId, String producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.ordenId = ordenId;
        this.productoId = productoId;
        this.producto = producto;
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
