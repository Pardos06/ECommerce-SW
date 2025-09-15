package com.ecommerce.app.orden.domain.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.app.pago.domain.models.MetodoPago;
import com.ecommerce.app.usuario.domain.models.Cliente;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrdenID")
    private Integer ordenId;

    @Column(name = "FechaOrden", nullable = false)
    private LocalDateTime fechaOrden = LocalDateTime.now();

    @Column(name = "Estado", nullable = false, length = 50)
    private String estado = "Pendiente";

    @Column(name = "EstadoEmail", nullable = false, length = 50)
    private String estadoEmail = "En espera";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteID", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MetodoPagoID", nullable = false)
    private MetodoPago metodoPago;

    @OneToMany(mappedBy = "orden", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrdenDetails> detalles;

    public Orden() {}
    public Orden(Integer ordenId, LocalDateTime fechaOrden, String estado, String estadoEmail, Cliente cliente,
            MetodoPago metodoPago, List<OrdenDetails> detalles) {
        super();
        this.ordenId = ordenId;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.estadoEmail = estadoEmail;
        this.cliente = cliente;
        this.metodoPago = metodoPago;
        this.detalles = detalles;
    }
    public Integer getOrdenId() {
        return ordenId;
    }
    public void setOrdenId(Integer ordenId) {
        this.ordenId = ordenId;
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
    public List<OrdenDetails> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<OrdenDetails> detalles) {
        this.detalles = detalles;
    }
}