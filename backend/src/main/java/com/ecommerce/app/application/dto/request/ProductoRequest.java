package com.ecommerce.app.application.dto.request;

import java.math.BigDecimal;

public record ProductoRequest (
        Integer id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        int stock,
        String imagenNombre,
        int categoriaId
) {}
