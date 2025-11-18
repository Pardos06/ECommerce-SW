package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.CompraDetailsRequest;
import com.ecommerce.app.application.dto.response.CompraDetailsResponse;
import com.ecommerce.app.application.mapper.CompraDetailsMapper;
import com.ecommerce.app.domain.models.Compra;
import com.ecommerce.app.domain.models.CompraDetails;
import com.ecommerce.app.domain.models.Producto;
import com.ecommerce.app.domain.repository.CompraDetailsRepository;
import com.ecommerce.app.domain.repository.CompraRepository;
import com.ecommerce.app.domain.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraDetailsService {

    private final CompraDetailsRepository compraDetailsRepository;
    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;

    public CompraDetailsService(CompraDetailsRepository compraDetailsRepository, CompraRepository compraRepository, ProductoRepository productoRepository) {
        this.compraDetailsRepository = compraDetailsRepository;
        this.compraRepository = compraRepository;
        this.productoRepository = productoRepository;
    }

    public List<CompraDetailsResponse> listarDetallesCompra() {
        return compraDetailsRepository.findAll()
                .stream()
                .map(CompraDetailsMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CompraDetailsResponse obtenerPorId(int id) {
        CompraDetails compraDetails = compraDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle de compra no encontrado"));

        return CompraDetailsMapper.toResponse(compraDetails);
    }

    @Transactional
    public CompraDetailsResponse crearCompraDetalle(CompraDetailsRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto inválido."));

        Compra compra = compraRepository.findById(request.compraId())
                .orElseThrow(() -> new IllegalArgumentException("Compra inválida."));

        producto.setStock(producto.getStock() + request.cantidad());
        productoRepository.save(producto);

        CompraDetails compraDetails = new CompraDetails();
        compraDetails.setId(null);
        compraDetails.setCantidad(request.cantidad());
        compraDetails.setPrecioUnitario(request.precioUnitario());
        compraDetails.setProducto(producto);
        compraDetails.setCompra(compra);

        CompraDetails compraDetailsGuardado = compraDetailsRepository.save(compraDetails);

        return CompraDetailsMapper.toResponse(compraDetailsGuardado);
    }

    @Transactional
    public CompraDetailsResponse editarCompraDetalle(CompraDetailsRequest request) {
        CompraDetails compraDetails = compraDetailsRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Detalle de compra no encontrado con id: " + request.id()));

        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto inválido."));

        Compra compra = compraRepository.findById(request.compraId())
                .orElseThrow(() -> new IllegalArgumentException("Compra inválida."));

        int diferenciaCantidad = request.cantidad() - compraDetails.getCantidad();
        producto.setStock(producto.getStock() + diferenciaCantidad);
        productoRepository.save(producto);

        compraDetails.setCantidad(request.cantidad());
        compraDetails.setPrecioUnitario(request.precioUnitario());
        compraDetails.setProducto(producto);
        compraDetails.setCompra(compra);

        CompraDetails actualizado = compraDetailsRepository.save(compraDetails);

        return CompraDetailsMapper.toResponse(actualizado);
    }

    @Transactional
    public void eliminarCompraDetalle(int id) {
        if (!compraDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("Detalle de compra no encontrado");
        }

        compraDetailsRepository.deleteById(id);
    }
}
