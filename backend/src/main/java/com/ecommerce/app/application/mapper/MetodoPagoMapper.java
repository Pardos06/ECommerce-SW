package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.MetodoPagoRequest;
import com.ecommerce.app.application.dto.response.MetodoPagoResponse;
import com.ecommerce.app.domain.models.MetodoPago;

public class MetodoPagoMapper {

    public static MetodoPago toEntity(MetodoPagoRequest request) {
        if (request == null) {
            return null;
        }

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setId(request.id());
        metodoPago.setNombre(request.nombre());
        return metodoPago;
    }

    public static MetodoPagoResponse toResponse(MetodoPago metodoPago) {
        if (metodoPago == null) {
            return null;
        }

        return new MetodoPagoResponse(
                metodoPago.getId(),
                metodoPago.getNombre()
        );
    }
}
