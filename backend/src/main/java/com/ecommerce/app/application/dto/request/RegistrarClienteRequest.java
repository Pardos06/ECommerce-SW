package com.ecommerce.app.application.dto.request;

public record RegistrarClienteRequest (
        String nombre,
        String email,
        String contrasena,
        String telefono,
        String direccion
) {}