package com.ecommerce.app.application.dto.response;

import java.math.BigDecimal;

public class CompraDetailsResponse {
    private int id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private String producto;
    private int productoId;
    private int compraId;

    public CompraDetailsResponse() {
    }

    public CompraDetailsResponse(int id, int cantidad, BigDecimal precioUnitario, String producto, int productoId, int compraId) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.productoId = productoId;
        this.compraId = compraId;
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }
}
