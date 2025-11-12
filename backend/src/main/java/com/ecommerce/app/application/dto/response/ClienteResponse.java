package com.ecommerce.app.application.dto.response;

public record ClienteResponse (
        int id,
        String telefono,
        String direccion,
        String usuario,
        String email
) {}