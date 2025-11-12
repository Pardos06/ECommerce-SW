package com.ecommerce.app.application.dto.response;

public record EmpleadoResponse (
        int id,
        int usuarioId,
        String nombreUsuario,
        String emailUsuario,
        String area,
        String cargo
) {}