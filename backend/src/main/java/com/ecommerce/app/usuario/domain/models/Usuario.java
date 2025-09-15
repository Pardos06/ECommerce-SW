package com.ecommerce.app.usuario.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotNull
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String passwordHash;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String estado = "Activo";

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Rol rol;

    public Usuario() {}
    
    public Usuario(int id, String nombre, String email, String passwordHash, String estado, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.estado = estado;
        this.rol = rol;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

}