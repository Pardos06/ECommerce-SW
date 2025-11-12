package com.ecommerce.app.application.dto.request;

import java.time.LocalDateTime;

public record CompraRequest (
        Integer id,
        LocalDateTime fechaCompra,
        String estado,
        int metodoPagoId,
        int proveedorId,
        int empleadoId
) {}
