package com.ecommerce.app.orden.domain.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.ecommerce.app.catalogo.domain.models.Producto;
import jakarta.validation.constraints.*;

@Entity
public class OrdenDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private int cantidad;

    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 18, fraction = 2)
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal precioUnitario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_orden_details_orden"))
    private Orden orden;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_orden_details_producto"))
    private Producto producto;

    public OrdenDetails() {}

    public OrdenDetails(int id, int cantidad, BigDecimal precioUnitario, Orden orden, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.orden = orden;
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