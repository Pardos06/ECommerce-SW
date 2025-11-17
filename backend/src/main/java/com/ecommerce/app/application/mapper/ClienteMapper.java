package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.ClienteRequest;
import com.ecommerce.app.application.dto.response.ClienteResponse;
import com.ecommerce.app.domain.models.Cliente;
import com.ecommerce.app.domain.models.Usuario;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequest request, Usuario usuario) {
        if (request == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(request.id());
        cliente.setUsuario(usuario);
        cliente.setTelefono(request.telefono());
        cliente.setDireccion(request.direccion());

        return cliente;
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteResponse(
                cliente.getId(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getUsuario().getNombre(),
                cliente.getUsuario().getEmail()
        );
    }
}
