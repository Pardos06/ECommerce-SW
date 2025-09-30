package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.CompraRequest;
import com.ecommerce.app.application.dto.response.CompraResponse;
import com.ecommerce.app.domain.models.Compra;
import com.ecommerce.app.domain.models.Empleado;
import com.ecommerce.app.domain.models.MetodoPago;
import com.ecommerce.app.domain.models.Proveedor;

public class CompraMapper {

    public static Compra toEntity(CompraRequest request, MetodoPago metodoPago, Proveedor proveedor, Empleado empleado) {
        if (request == null) {
            return null;
        }

        Compra compra = new Compra();
        compra.setId(request.getId());
        compra.setFechaCompra(request.getFechaCompra());
        compra.setEstado(request.getEstado());
        compra.setMetodoPago(metodoPago);
        compra.setProveedor(proveedor);
        compra.setEmpleado(empleado);
        return compra;
    }

    public static CompraResponse toResponse(Compra compra) {
        if (compra == null) {
            return null;
        }

        return new CompraResponse(
                compra.getId(),
                compra.getFechaCompra(),
                compra.getEstado(),
                compra.getProveedor().getNombre(),
                compra.getEmpleado().getUsuario().getNombre()
        );
    }
}
