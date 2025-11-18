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
        producto.setId(request.id());
        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setImagenNombre(request.imagenNombre());
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