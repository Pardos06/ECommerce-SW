package com.ecommerce.app.domain.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 150)
    @Column(length = 150, nullable = false)
    private String nombre;

    @Size(max = 500)
    @Column(length = 500)
    private String descripcion;

    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 16, fraction = 2)
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal precio;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private int stock;

    @Size(max = 10)
    @Column(length = 10,
            insertable = false,
            updatable = false,
            columnDefinition = "VARCHAR(10) GENERATED ALWAYS AS (CASE WHEN stock > 0 THEN 'Disponible' ELSE 'Agotado' END) STORED"
    )
    private String disponibilidad;

    @Size(max = 255)
    private String imagenNombre;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CompraDetails> compras = new ArrayList<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrdenDetails> ordenes = new ArrayList<>();

    public Producto() {
    }

    public Producto(Integer id, String nombre, String descripcion, BigDecimal precio, int stock, String disponibilidad, String imagenNombre, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.disponibilidad = disponibilidad;
        this.imagenNombre = imagenNombre;
        this.categoria = categoria;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getImagenNombre() {
        return imagenNombre;
    }

    public void setImagenNombre(String imagenNombre) {
        this.imagenNombre = imagenNombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<CompraDetails> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraDetails> compras) {
        this.compras = compras;
    }

    public List<OrdenDetails> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<OrdenDetails> ordenes) {
        this.ordenes = ordenes;
    }
}
