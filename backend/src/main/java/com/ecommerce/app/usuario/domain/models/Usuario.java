package com.ecommerce.app.usuario.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuarioID")
    private Integer usuarioId;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "PasswordHash", nullable = false, length = 256)
    private String passwordHash;

    @Column(name = "Estado", nullable = false, length = 50)
    private String estado = "Activo";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RolID", nullable = false)
    private Roles rol;

    public Usuarios() {}
    
    public Usuarios(Integer usuarioId, String nombre, String email, String passwordHash, String estado, Roles rol) {
        super();
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.estado = estado;
        this.rol = rol;
    }
    public Integer getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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
    public Roles getRol() {
        return rol;
    }
    public void setRol(Roles rol) {
        this.rol = rol;
    }

}