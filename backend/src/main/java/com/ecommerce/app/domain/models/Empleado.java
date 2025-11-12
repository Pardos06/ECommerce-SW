package com.ecommerce.app.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_empleado_usuario"))
    private Usuario usuario;

    @Size(max = 100)
    @Column(length = 100)
    private String area;

    @Size(max = 100)
    @Column(length = 100)
    private String cargo;

    public Empleado() {}
    public Empleado(Integer id, Usuario usuario, String area, String cargo) {
        this.id = id;
        this.usuario = usuario;
        this.area = area;
        this.cargo = cargo;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}