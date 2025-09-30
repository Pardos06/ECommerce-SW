package com.ecommerce.app.domain.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime fechaCompra = LocalDateTime.now();

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String estado = "Pendiente";

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_compra_metodo_pago"))
    private MetodoPago metodoPago;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_compra_proveedor"))
    private Proveedor proveedor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_compra_empleado"))
    private Empleado empleado;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompraDetails> details = new ArrayList<>();


    public Compra() {}
    public Compra(int id, LocalDateTime fechaCompra, String estado, MetodoPago metodoPago,
            Proveedor proveedor, Empleado empleado) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.proveedor = proveedor;
        this.empleado = empleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<CompraDetails> getDetails() {
        return details;
    }

    public void setDetails(List<CompraDetails> details) {
        this.details = details;
    }
}