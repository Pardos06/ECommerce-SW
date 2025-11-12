package com.ecommerce.app.application.dto.response;

import java.time.LocalDateTime;

public record CompraResponse (
    int id,
    LocalDateTime fechaCompra,
    String estado,
    String proveedor,
    String empleado
) {}