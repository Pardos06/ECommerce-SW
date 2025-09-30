package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.CompraDetailsRequest;
import com.ecommerce.app.application.dto.response.CompraDetailsResponse;
import com.ecommerce.app.domain.models.Compra;
import com.ecommerce.app.domain.models.CompraDetails;
import com.ecommerce.app.domain.models.Producto;

public class CompraDetailsMapper {

    public static CompraDetails toEntity(CompraDetailsRequest request, Producto producto, Compra compra) {
        if (request == null) {
            return null;
        }

        CompraDetails compraDetails = new CompraDetails();
        compraDetails.setId(request.getId());
        compraDetails.setCantidad(request.getCantidad());
        compraDetails.setPrecioUnitario(request.getPrecioUnitario());
        compraDetails.setProducto(producto);
        compraDetails.setCompra(compra);
        return compraDetails;
    }

    public static CompraDetailsResponse toResponse(CompraDetails compraDetails) {
        if (compraDetails == null) {
            return null;
        }

        return new CompraDetailsResponse(
                compraDetails.getId(),
                compraDetails.getCantidad(),
                compraDetails.getPrecioUnitario(),
                compraDetails.getProducto().getNombre(),
                compraDetails.getProducto().getId(),
                compraDetails.getCompra().getId()
        );
    }
}
