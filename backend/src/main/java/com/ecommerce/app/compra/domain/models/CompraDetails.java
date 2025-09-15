package com.ecommerce.app.compra.domain.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.ecommerce.app.catalogo.domain.models.Producto;

@Entity
@Table(name = "compradetails")
public class CompraDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CompraDetailsID")
    private Integer compraDetailsId;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "PrecioUnitario", nullable = false, precision = 18, scale = 2)
    private BigDecimal precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductoID", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CompraID", nullable = false)
    private Compra compra;

    public CompraDetails() {}
    public CompraDetails(Integer compraDetailsId, Integer cantidad, BigDecimal precioUnitario, Producto producto,
            Compra compra) {
        super();
        this.compraDetailsId = compraDetailsId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.compra = compra;
    }
    public Integer getCompraDetailsId() {
        return compraDetailsId;
    }
    public void setCompraDetailsId(Integer compraDetailsId) {
        this.compraDetailsId = compraDetailsId;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
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