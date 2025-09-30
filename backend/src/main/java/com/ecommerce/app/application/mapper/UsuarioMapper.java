package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.UsuarioRequest;
import com.ecommerce.app.application.dto.response.UsuarioResponse;
import com.ecommerce.app.domain.models.Rol;
import com.ecommerce.app.domain.models.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest request, Rol rol) {
        if (request == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(request.getId());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(request.getPasswordHash());
        usuario.setEstado(request.getEstado());
        usuario.setRol(rol);
        return usuario;
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getEstado(),
                usuario.getRol().getNombre(),
                usuario.getCliente().getId(),
                usuario.getEmpleado().getId()
        );
    }
}
