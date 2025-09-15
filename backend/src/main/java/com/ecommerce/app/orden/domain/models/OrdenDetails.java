package com.ecommerce.app.orden.domain.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.ecommerce.app.catalogo.domain.models.Producto;

@Entity
@Table(name = "ordendetails")
public class OrdenDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrdenDetailsID")
    private Integer ordenDetailsId;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "PrecioUnit", nullable = false, precision = 18, scale = 2)
    private BigDecimal precioUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrdenID", nullable = false)
    private Orden orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductoID", nullable = false)
    private Producto producto;

    public OrdenDetails() {}
    public OrdenDetails(Integer ordenDetailsId, Integer cantidad, BigDecimal precioUnit, Orden orden,
            Producto producto) {
        super();
        this.ordenDetailsId = ordenDetailsId;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
        this.orden = orden;
        this.producto = producto;
    }
    public Integer getOrdenDetailsId() {
        return ordenDetailsId;
    }
    public void setOrdenDetailsId(Integer ordenDetailsId) {
        this.ordenDetailsId = ordenDetailsId;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getPrecioUnit() {
        return precioUnit;
    }
    public void setPrecioUnit(BigDecimal precioUnit) {
        this.precioUnit = precioUnit;
    }
    public Orden getOrden() {
        return orden;
    }
    public void setOrden(Orden orden) {
        this.orden = orden;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}