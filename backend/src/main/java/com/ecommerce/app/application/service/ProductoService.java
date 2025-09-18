package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.ProductoRequest;
import com.ecommerce.app.application.dto.response.ProductoResponse;
import com.ecommerce.app.application.mapper.ProductoMapper;
import com.ecommerce.app.domain.models.Categoria;
import com.ecommerce.app.domain.models.Producto;
import com.ecommerce.app.domain.repository.CategoriaRepository;
import com.ecommerce.app.domain.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProductoResponse> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponse obtenerPorId(int id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ProductoMapper::toResponse).orElse(null);
    }

    public ProductoResponse registrarProducto(ProductoRequest request) {
        Optional<Categoria> categoria = categoriaRepository.findById(request.getCategoriaId());
        if (categoria.isEmpty())
            return null;

        Producto producto = ProductoMapper.toEntity(request, categoria.get());
        Producto productoGuardado = productoRepository.save((producto));

        return ProductoMapper.toResponse(productoGuardado);
    }

    public ProductoResponse editarProducto(ProductoRequest request) {
        Optional<Producto> producto = productoRepository.findById(request.getId());
        if (producto.isEmpty()) {
            return null;
        }

        Optional<Categoria> categoria = categoriaRepository.findById(request.getCategoriaId());
        if (categoria.isEmpty()) {
            return null;
        }

        Producto p = producto.get();
        p.setNombre(request.getNombre());
        p.setDescripcion(request.getDescripcion());
        p.setPrecio(request.getPrecio());
        p.setStock(request.getStock());
        p.setCategoria(categoria.get());

        productoRepository.save(p);

        return ProductoMapper.toResponse(p);
    }

    public void eliminarProducto(int id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        // Agregar validación para productos relacionados con compra o ventas

        productoRepository.delete(producto);
    }
}
