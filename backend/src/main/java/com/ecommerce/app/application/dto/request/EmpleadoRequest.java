package com.ecommerce.app.application.dto.request;

public record EmpleadoRequest (
        Integer id,
        int usuarioId,
        String area,
        String cargo
) {}