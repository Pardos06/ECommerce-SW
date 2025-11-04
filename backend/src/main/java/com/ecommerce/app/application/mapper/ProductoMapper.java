package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.ProductoRequest;
import com.ecommerce.app.application.dto.response.ProductoResponse;
import com.ecommerce.app.domain.models.Categoria;
import com.ecommerce.app.domain.models.Producto;

public class ProductoMapper {

    public static Producto toEntity(ProductoRequest request, Categoria categoria) {
        if (request == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setId(request.getId());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setImagenNombre(request.getImagenNombre());
        producto.setCategoria(categoria);
        return producto;
    }

    public static ProductoResponse toResponse(Producto producto) {
        if (producto == null) {
            return null;
        }

        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                (producto.getStock() > 0 ? "Disponible" : "Agotado"),
                producto.getCategoria() != null ? producto.getCategoria().getNombre() : null,
                producto.getImagenNombre()
        );
    }
}