package com.ecommerce.app.pago.domain.models;

import jakarta.persistence.*;
import java.util.List;

import com.ecommerce.app.compra.domain.models.Compra;
import com.ecommerce.app.orden.domain.models.Orden;

@Entity
@Table(name = "metodopago")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MetodoPagoID")
    private Integer metodoPagoId;

    @Column(name = "Nombre", nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "metodoPago", fetch = FetchType.LAZY)
    private List<Compra> compras;

    @OneToMany(mappedBy = "metodoPago", fetch = FetchType.LAZY)
    private List<Orden> ordenes;

    public MetodoPago() {}
    public MetodoPago(Integer metodoPagoId, String nombre, List<Compra> compras, List<Orden> ordenes) {
        super();
        this.metodoPagoId = metodoPagoId;
        this.nombre = nombre;
        this.compras = compras;
        this.ordenes = ordenes;
    }
    public Integer getMetodoPagoId() {
        return metodoPagoId;
    }
    public void setMetodoPagoId(Integer metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Compra> getCompras() {
        return compras;
    }
    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
    public List<Orden> getOrdenes() {
        return ordenes;
    }
    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }
    
}
