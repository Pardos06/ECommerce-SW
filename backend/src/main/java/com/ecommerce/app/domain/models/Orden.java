package com.ecommerce.app.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
public class  Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime fechaOrden = LocalDateTime.now();

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String estado = "Pendiente";

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String estadoEmail = "En espera";

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_orden_cliente"))
    private Cliente cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_orden_metodo_pago"))
    private MetodoPago metodoPago;

    @OneToMany(mappedBy = "orden", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrdenDetails> details = new ArrayList<>();

    public Orden() {}

    public Orden(int id, LocalDateTime fechaOrden, String estado, String estadoEmail, Cliente cliente, MetodoPago metodoPago) {
        this.id = id;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.estadoEmail = estadoEmail;
        this.cliente = cliente;
        this.metodoPago = metodoPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDateTime fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoEmail() {
        return estadoEmail;
    }

    public void setEstadoEmail(String estadoEmail) {
        this.estadoEmail = estadoEmail;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<OrdenDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrdenDetails> details) {
        this.details = details;
    }
}