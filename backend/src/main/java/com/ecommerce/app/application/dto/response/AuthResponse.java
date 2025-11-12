package com.ecommerce.app.application.dto.response;

public record AuthResponse (
        String token,
        String rol,
        String nombre
) {}