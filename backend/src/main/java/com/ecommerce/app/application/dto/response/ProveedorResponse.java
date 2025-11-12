package com.ecommerce.app.application.dto.response;

public record ProveedorResponse (
        int id,
        String nombre,
        Integer telefono,
        String email,
        String direccion,
        String tipoProveedor
) {}
