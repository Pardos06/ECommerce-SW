package com.ecommerce.app.compra.domain.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipoproveedor")
public class TipoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipoProveedorID")
    private Integer tipoProveedorId;

    @Column(name = "Nombre", nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "tipoProveedor", fetch = FetchType.LAZY)
    private List<Proveedor> proveedores;

    public TipoProveedor() {}
    public TipoProveedor(Integer tipoProveedorId, String nombre, List<Proveedor> proveedores) {
        super();
        this.tipoProveedorId = tipoProveedorId;
        this.nombre = nombre;
        this.proveedores = proveedores;
    }
    public Integer getTipoProveedorId() {
        return tipoProveedorId;
    }
    public void setTipoProveedorId(Integer tipoProveedorId) {
        this.tipoProveedorId = tipoProveedorId;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Proveedor> getProveedores() {
        return proveedores;
    }
    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

}