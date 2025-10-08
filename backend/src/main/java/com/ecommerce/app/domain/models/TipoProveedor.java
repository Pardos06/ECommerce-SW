package com.ecommerce.app.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TipoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "tipoProveedor", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Proveedor> proveedores = new ArrayList<>();

    public TipoProveedor() {}
    public TipoProveedor(int id, String nombre, List<Proveedor> proveedores) {
        super();
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

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }
}