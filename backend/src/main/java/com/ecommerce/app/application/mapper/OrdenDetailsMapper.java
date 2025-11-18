package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.OrdenDetailsRequest;
import com.ecommerce.app.application.dto.response.OrdenDetailsResponse;
import com.ecommerce.app.domain.models.Orden;
import com.ecommerce.app.domain.models.OrdenDetails;
import com.ecommerce.app.domain.models.Producto;

public class OrdenDetailsMapper {

    public static OrdenDetails toEntity(OrdenDetailsRequest request, Orden orden, Producto producto) {
        if (request == null) {
            return null;
        }

        OrdenDetails ordenDetails = new OrdenDetails();
        ordenDetails.setId(request.id());
        ordenDetails.setCantidad(request.cantidad());
        ordenDetails.setPrecioUnitario(request.precioUnitario());
        ordenDetails.setOrden(orden);
        ordenDetails.setProducto(producto);
        return ordenDetails;
    }

    public static OrdenDetailsResponse toResponse(OrdenDetails ordenDetails) {
        if (ordenDetails == null) {
            return null;
        }

        return new OrdenDetailsResponse(
                ordenDetails.getId(),
                ordenDetails.getCantidad(),
                ordenDetails.getPrecioUnitario(),
                ordenDetails.getOrden().getId(),
                ordenDetails.getProducto().getId(),
                ordenDetails.getProducto().getNombre()
        );
    }
}
