package com.ecommerce.app.catalogo.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoID")
    private Integer productoId;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(length = 10)
    private String disponibilidad;

    @Column(length = 255)
    private String imagenNombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoriaID", nullable = false)
    private Categorias categoria;

    public Producto() {}

    public Producto(Integer productoId, String nombre, String descripcion, Double precio, Integer stock, String disponibilidad,
            String imagenNombre, Categorias categoria) {
        super();
        this.productoId = productoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.disponibilidad = disponibilidad;
        this.imagenNombre = imagenNombre;
        this.categoria = categoria;
    }
    public Integer getProductoId() {
        return productoId;
    }
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
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
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
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
    public Categorias getCategoria() {
        return categoria;
    }
    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }
    
}