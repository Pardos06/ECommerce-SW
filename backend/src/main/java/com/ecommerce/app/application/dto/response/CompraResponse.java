package com.ecommerce.app.application.dto.response;

import java.time.LocalDate;

public record CompraResponse (
    int id,
    LocalDate fechaCompra,
    String estado,
    String proveedor,
    String empleado
) {}