package com.ecommerce.app.application.dto.request;

import java.time.LocalDate;

public record CompraRequest (
        Integer id,
        LocalDate fechaCompra,
        String estado,
        int metodoPagoId,
        int proveedorId,
        int empleadoId
) {}
