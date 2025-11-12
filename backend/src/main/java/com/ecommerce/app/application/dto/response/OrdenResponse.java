package com.ecommerce.app.application.dto.response;

public record OrdenResponse (
        int id,
        String estado,
        String estadoEmail,
        String cliente,
        String metodoPago,
        int clienteId,
        int metodoPagoId
) {}