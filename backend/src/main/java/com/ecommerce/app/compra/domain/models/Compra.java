package com.ecommerce.app.compra.domain.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.app.compra.domain.repository.CompraDetailsRepository;
import com.ecommerce.app.pago.domain.models.MetodoPago;
import com.ecommerce.app.usuario.domain.models.Empleado;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CompraID")
    private Integer compraId;

    @Column(name = "FechaCompra", nullable = false)
    private LocalDateTime fechaCompra = LocalDateTime.now();

    @Column(name = "Estado", nullable = false, length = 50)
    private String estado = "Pendiente";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MetodoPagoID", nullable = false)
    private MetodoPago metodoPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProveedorID", nullable = false)
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmpleadoID", nullable = false)
    private Empleado empleado;

    @OneToMany(mappedBy = "compra", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CompraDetailsRepository> detalles;

    public Compra() {}
    public Compra(Integer compraId, LocalDateTime fechaCompra, String estado, MetodoPago metodoPago,
            Proveedor proveedor, Empleado empleado, List<CompraDetailsRepository> detalles) {
        super();
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.proveedor = proveedor;
        this.empleado = empleado;
        this.detalles = detalles;
    }
    public Integer getCompraId() {
        return compraId;
    }
    public void setCompraId(Integer compraId) {
        this.compraId = compraId;
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
}