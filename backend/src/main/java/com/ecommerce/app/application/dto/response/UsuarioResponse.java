package com.ecommerce.app.application.dto.response;

public record UsuarioResponse (
        int id,
        String nombre,
        String email,
        String estado,
        String rol,
        Integer clienteId,
        Integer empleadoId
) {}
