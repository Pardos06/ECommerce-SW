package com.ecommerce.app.application.dto.response;

import java.math.BigDecimal;

public record ProductoResponse (
        int id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        int stock,
        String disponibilidad,
        String categoria,
        String imagenNombre
) {}