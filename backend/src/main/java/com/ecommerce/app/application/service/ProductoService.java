package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.ProductoRequest;
import com.ecommerce.app.application.dto.response.ProductoImagenResponse;
import com.ecommerce.app.application.dto.response.ProductoResponse;
import com.ecommerce.app.application.mapper.ProductoMapper;
import com.ecommerce.app.domain.models.Categoria;
import com.ecommerce.app.domain.models.Producto;
import com.ecommerce.app.domain.repository.CategoriaRepository;
import com.ecommerce.app.domain.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final String CARPETA_IMAGENES = Paths.get(System.getProperty("user.dir"), "wwwroot", "imagenes").toString();

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoResponse> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponse obtenerPorId(int id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID " + id));
        return ProductoMapper.toResponse(producto);
    }

    public ProductoResponse registrarProducto(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la categoría con ID " + request.getCategoriaId()));

        Producto producto = ProductoMapper.toEntity(request, categoria);
        Producto productoGuardado = productoRepository.save((producto));

        return ProductoMapper.toResponse(productoGuardado);
    }

    public ProductoResponse editarProducto(ProductoRequest request) {
        Producto producto = productoRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el producto con ID " + request.getId()));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la categoría con ID " + request.getCategoriaId()));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);

        productoRepository.save(producto);

        return ProductoMapper.toResponse(producto);
    }

    public void eliminarProducto(int id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        if (producto.getCompras() != null && !producto.getCompras().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un producto asociado a una compra");
        }

        if (producto.getOrdenes() != null && !producto.getOrdenes().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un producto asociado a una orden");
        }

        productoRepository.delete(producto);
    }

    public ProductoImagenResponse subirImagen(MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("Archivo no válido");
        }

        File dir = new File(CARPETA_IMAGENES);
        if (!dir.exists()) dir.mkdirs();

        String nombreArchivo = UUID.randomUUID() + "-" + archivo.getOriginalFilename();
        String rutaCompleta = Paths.get(CARPETA_IMAGENES, nombreArchivo).toString();
        archivo.transferTo(new File(rutaCompleta));

        String url = "/imagenes/" + nombreArchivo;
        return new ProductoImagenResponse(nombreArchivo, url);
    }
    
    public List<ProductoResponse> buscarProductosDisponibles(String nombre) {
    	return productoRepository.buscarProductosDisponibles(nombre)
    			.stream()
    			.map(ProductoMapper::toResponse)
    			.toList();
    }
}
