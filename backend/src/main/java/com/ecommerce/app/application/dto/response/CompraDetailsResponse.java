package com.ecommerce.app.application.dto.response;

import java.math.BigDecimal;

public record CompraDetailsResponse (
        int id,
        int cantidad,
        BigDecimal precioUnitario,
        String producto,
        int productoId,
        int compraId
) {}