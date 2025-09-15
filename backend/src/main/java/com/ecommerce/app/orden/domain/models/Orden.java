package com.ecommerce.app.orden.domain.models;

import java.time.LocalDateTime;

import com.ecommerce.app.pago.domain.models.MetodoPago;
import com.ecommerce.app.usuario.domain.models.Cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}