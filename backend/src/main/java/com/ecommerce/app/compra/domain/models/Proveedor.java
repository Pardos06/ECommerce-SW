package com.ecommerce.app.compra.domain.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProveedorID")
    private Integer proveedorId;

    @Column(name = "Nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "Telefono")
    private Integer telefono;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "Direccion", length = 100)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoProveedorID", nullable = false)
    private TipoProveedor tipoProveedor;

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<Compra> compras;

    public Proveedor() {}
    public Proveedor(Integer proveedorId, String nombre, Integer telefono, String email, String direccion,
            TipoProveedor tipoProveedor, List<Compra> compras) {
        super();
        this.proveedorId = proveedorId;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.tipoProveedor = tipoProveedor;
        this.compras = compras;
    }
    public Integer getProveedorId() {
        return proveedorId;
    }
    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getTelefono() {
        return telefono;
    }
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public TipoProveedor getTipoProveedor() {
        return tipoProveedor;
    }
    public void setTipoProveedor(TipoProveedor tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }
    public List<Compra> getCompras() {
        return compras;
    }
    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

}