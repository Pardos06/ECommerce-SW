package com.ecommerce.app.usuario.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpleadoID")
    private Integer empleadoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioID", nullable = false, unique = true)
    private Usuarios usuario;

    @Column(name = "Area", length = 100)
    private String area;

    @Column(name = "Cargo", length = 100)
    private String cargo;

    public Empleado() {}
    public Empleado(Integer empleadoId, Usuarios usuario, String area, String cargo) {
        super();
        this.empleadoId = empleadoId;
        this.usuario = usuario;
        this.area = area;
        this.cargo = cargo;
    }
    public Integer getEmpleadoId() {
        return empleadoId;
    }
    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }
    public Usuarios getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuarios usuario) {
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