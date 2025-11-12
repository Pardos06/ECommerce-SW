package com.ecommerce.app.application.dto.request;

import java.math.BigDecimal;

public record CompraDetailsRequest (
        Integer id,
        int cantidad,
        BigDecimal precioUnitario,
        int productoId,
        int compraId
) {}