package com.ecommerce.app.application.dto.response;

import java.math.BigDecimal;

public class ProductoResponse {
    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private String disponibilidad;
    private int categoriaId;
    private String categoria;
    private String imagenNombre;

    public ProductoResponse() {
    }

    public ProductoResponse(int id, String nombre, String descripcion, BigDecimal precio, int stock, String disponibilidad,int categoriaId, String categoria, String imagenNombre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.disponibilidad = disponibilidad;
        this.categoriaId = categoriaId;
        this.categoria = categoria;
        this.imagenNombre = imagenNombre;
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
    public int getCategoriaId() {   
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagenNombre() {
        return imagenNombre;
    }

    public void setImagenNombre(String imagenNombre) {
        this.imagenNombre = imagenNombre;
    }
}
