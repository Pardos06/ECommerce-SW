package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.TipoProveedorRequest;
import com.ecommerce.app.application.dto.response.TipoProveedorResponse;
import com.ecommerce.app.domain.models.TipoProveedor;

public class TipoProveedorMapper {

    public static TipoProveedor toEntity(TipoProveedorRequest request) {
        if (request == null) {
            return null;
        }

        TipoProveedor tipoProveedor = new TipoProveedor();
        tipoProveedor.setId(request.id());
        tipoProveedor.setNombre(request.nombre());
        return tipoProveedor;
    }

    public static TipoProveedorResponse toResponse(TipoProveedor tipoProveedor) {
        if (tipoProveedor == null) {
            return null;
        }

        return new TipoProveedorResponse(
                tipoProveedor.getId(),
                tipoProveedor.getNombre()
        );
    }
}
