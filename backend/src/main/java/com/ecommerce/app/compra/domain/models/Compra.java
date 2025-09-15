package com.ecommerce.app.compra.domain.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.app.compra.domain.repository.CompraDetailsRepository;
import com.ecommerce.app.pago.domain.models.MetodoPago;
import com.ecommerce.app.usuario.domain.models.Empleado;
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


    public Compra() {}
    public Compra(int id, LocalDateTime fechaCompra, String estado, MetodoPago metodoPago,
            Proveedor proveedor, Empleado empleado, List<CompraDetailsRepository> detalles) {
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
}