package com.ecommerce.app.application.dto.request;

import java.math.BigDecimal;

public class CompraDetailsRequest {
    private int id;
    private int cantidad;
    private BigDecimal precioUnitario;
    private int productoId;
    private int compraId;

    public CompraDetailsRequest() {
    }

    public CompraDetailsRequest(int id, int cantidad, BigDecimal precioUnitario, int productoId, int compraId) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
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
