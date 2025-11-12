package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.CategoriaRequest;
import com.ecommerce.app.application.dto.response.CategoriaResponse;
import com.ecommerce.app.domain.models.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequest request) {
        if (request == null) {
            return null;
        }

        Categoria categoria = new Categoria();
        categoria.setId(request.id());
        categoria.setNombre(request.nombre());
        return categoria;
    }

    public static CategoriaResponse toResponse(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre()
        );
    }
}
