package com.ecommerce.app.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_usuario_cliente"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_usuario_empleado"))
    private Empleado empleado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_usuario_rol"))
    private Rol rol;

    public Usuario() {}

    public Usuario(Integer id, String nombre, String email, String passwordHash, String estado, Cliente cliente, Empleado empleado, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.estado = estado;
        this.cliente = cliente;
        this.empleado = empleado;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}