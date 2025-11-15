package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.EmpleadoRequest;
import com.ecommerce.app.application.dto.response.EmpleadoResponse;
import com.ecommerce.app.domain.models.Area;
import com.ecommerce.app.domain.models.Cargo;
import com.ecommerce.app.domain.models.Empleado;
import com.ecommerce.app.domain.models.Usuario;

public class EmpleadoMapper {

    public static Empleado toEntity(EmpleadoRequest request, Usuario usuario, Area area, Cargo cargo) {
        if (request == null) {
            return null;
        }

        Empleado empleado = new Empleado();
        empleado.setId(request.id());
        empleado.setArea(area);
        empleado.setCargo(cargo);
        empleado.setUsuario(usuario);

        return empleado;
    }

    public static EmpleadoResponse toResponse(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getUsuario().getId(),
                empleado.getUsuario().getNombre(),
                empleado.getUsuario().getEmail(),
                empleado.getArea().getNombre(),
                empleado.getCargo().getNombre()
        );
    }
}
