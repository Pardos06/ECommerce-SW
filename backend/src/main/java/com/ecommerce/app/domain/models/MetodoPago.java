package com.ecommerce.app.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "metodo_pago", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Compra> compras = new ArrayList<>();

    @OneToMany(mappedBy = "metodo_pago", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Orden> ordenes = new ArrayList<>();

    public MetodoPago() {
    }

    public MetodoPago(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
