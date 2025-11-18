package com.ecommerce.app.application.dto.request;

public record ProveedorRequest (
        Integer id,
        String nombre,
        int telefono,
        String email,
        String direccion,
        int tipoProveedorId
) {}
