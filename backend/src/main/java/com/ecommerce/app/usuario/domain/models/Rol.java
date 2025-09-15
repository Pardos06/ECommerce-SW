package com.ecommerce.app.usuario.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RolID")
    private Integer rolId;

    @Column(name = "NombreRol", nullable = false, length = 100)
    private String nombreRol;

    public Roles() {}

    public Roles(Integer rolId, String nombreRol) {
        super();
        this.rolId = rolId;
        this.nombreRol = nombreRol;
    }

    public Integer getRolId() {
        return rolId;
    }
    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }
    public String getNombreRol() {
        return nombreRol;
    }
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}