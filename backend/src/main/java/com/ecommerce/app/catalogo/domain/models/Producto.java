package com.ecommerce.app.catalogo.domain.models;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(length = 150, nullable = false)
    private String nombre;
    @Column(length = 500)
    private String descripcion;

    private double precio;
    
}
