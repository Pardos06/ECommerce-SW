package com.ecommerce.app.compra.domain.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.ecommerce.app.catalogo.domain.models.Producto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class CompraDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private int cantidad;

    @NotNull
    @Digits(integer = 18, fraction = 2)
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal precioUnitario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_compra_details_producto"))
    private Producto producto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_compra_details_compra"))
    private Compra compra;

    public CompraDetails() {}
    public CompraDetails(int id, int cantidad, BigDecimal precioUnitario, Producto producto,
            Compra compra) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.compra = compra;
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}