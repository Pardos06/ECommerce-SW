package com.ecommerce.app.application.dto.request;

import java.time.LocalDateTime;

public record OrdenRequest (
        Integer id,
        LocalDateTime fechaOrden,
        String estado,
        String estadoEmail,
        int clienteId,
        int metodoPagoId
) {}