package com.ecommerce.app.application.dto.request;

public record ClienteRequest (
		Integer id,
		int usuarioId,
		String telefono,
		String direccion
) {}
