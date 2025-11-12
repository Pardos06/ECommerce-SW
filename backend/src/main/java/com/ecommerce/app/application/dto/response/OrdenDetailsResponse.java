package com.ecommerce.app.application.dto.response;

import java.math.BigDecimal;

public record OrdenDetailsResponse (
        int id,
        int cantidad,
        BigDecimal precioUnitario,
        int ordenId,
        int productoId,
        String producto
) {}