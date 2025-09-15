package com.ecommerce.app.usuario.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteID")
    private Integer clienteId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioID", nullable = false, unique = true)
    private Usuarios usuario;

    @Column(name = "Telefono", length = 20)
    private String telefono;

    @Column(name = "Direccion", length = 200)
    private String direccion;

    public Cliente() {}

    public Cliente(Integer clienteId, Usuarios usuario, String telefono, String direccion) {
        super();
        this.clienteId = clienteId;
        this.usuario = usuario;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
    public Usuarios getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}