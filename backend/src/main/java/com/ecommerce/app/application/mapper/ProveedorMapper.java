package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.ProveedorRequest;
import com.ecommerce.app.application.dto.response.ProveedorResponse;
import com.ecommerce.app.domain.models.Proveedor;
import com.ecommerce.app.domain.models.TipoProveedor;

public class ProveedorMapper {

    public static Proveedor toEntity(ProveedorRequest request, TipoProveedor tipoProveedor) {
        if (request == null) {
            return null;
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setId(request.id());
        proveedor.setNombre(request.nombre());
        proveedor.setEmail(request.email());
        proveedor.setTelefono(request.telefono());
        proveedor.setDireccion(request.direccion());
        proveedor.setTipoProveedor(tipoProveedor);
        return proveedor;
    }

    public static ProveedorResponse toResponse(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }

        return new ProveedorResponse(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.getDireccion(),
                proveedor.getTipoProveedor().getNombre()
        );
    }
}
