package com.ecommerce.app.application.dto.request;

public record UsuarioRequest (
		Integer id,
		String nombre,
		String email,
		String passwordHash,
		String estado,
		int rolId
) {}
