package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.RolRequest;
import com.ecommerce.app.application.dto.response.RolResponse;
import com.ecommerce.app.domain.models.Rol;

public class RolMapper {

    public static Rol toEntity(RolRequest request) {
        if (request == null) {
            return null;
        }

        Rol rol = new Rol();
        rol.setId(request.id());
        rol.setNombre(request.nombre());
        return rol;
    }

    public static RolResponse toResponse(Rol rol) {
        if (rol == null) {
            return null;
        }

        return new RolResponse(
                rol.getId(),
                rol.getNombre()
        );
    }
}
