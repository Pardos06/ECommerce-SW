package com.ecommerce.app.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrdenResponse (
        int id,
        String estado,
        String estadoEmail,
        String cliente,
        String metodoPago,
        LocalDateTime fechaOrden,
        int clienteId,
        int metodoPagoId,
        List<OrdenDetailsResponse> details
) {}