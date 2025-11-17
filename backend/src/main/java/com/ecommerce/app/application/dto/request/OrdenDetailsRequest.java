package com.ecommerce.app.application.dto.request;

import java.math.BigDecimal;

public record OrdenDetailsRequest (
        Integer id,
        int cantidad,
        BigDecimal precioUnitario,
        int ordenId,
        int productoId
) {}