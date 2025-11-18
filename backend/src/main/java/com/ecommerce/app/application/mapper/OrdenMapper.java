package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.OrdenRequest;
import com.ecommerce.app.application.dto.response.OrdenResponse;
import com.ecommerce.app.domain.models.Cliente;
import com.ecommerce.app.domain.models.MetodoPago;
import com.ecommerce.app.domain.models.Orden;

public class OrdenMapper {

    public static Orden toEntity(OrdenRequest request, Cliente cliente, MetodoPago metodoPago) {
        if (request == null) {
            return null;
        }

        Orden orden = new Orden();
        orden.setId(request.id());
        orden.setFechaOrden(request.fechaOrden());
        orden.setEstado(request.estado());
        orden.setEstadoEmail(request.estadoEmail());
        orden.setCliente(cliente);
        orden.setMetodoPago(metodoPago);
        return orden;
    }

    public static OrdenResponse toResponse(Orden orden) {
        if (orden == null) {
            return null;
        }

        return new OrdenResponse(
                orden.getId(),
                orden.getEstado(),
                orden.getEstadoEmail(),
                orden.getCliente().getUsuario().getNombre(),
                orden.getMetodoPago().getNombre(),
                orden.getCliente().getId(),
                orden.getMetodoPago().getId()
        );
    }
}
