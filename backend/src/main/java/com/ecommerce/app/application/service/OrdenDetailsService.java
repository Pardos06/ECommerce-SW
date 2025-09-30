package com.ecommerce.app.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.request.OrdenDetailsRequest;
import com.ecommerce.app.application.dto.response.OrdenDetailsResponse;
import com.ecommerce.app.application.mapper.OrdenDetailsMapper;
import com.ecommerce.app.domain.models.Orden;
import com.ecommerce.app.domain.models.OrdenDetails;
import com.ecommerce.app.domain.models.Producto;
import com.ecommerce.app.domain.repository.OrdenDetailsRepository;
import com.ecommerce.app.domain.repository.OrdenRepository;
import com.ecommerce.app.domain.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrdenDetailsService {

    private final OrdenDetailsRepository ordenDetailsRepository;
    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;

    public OrdenDetailsService(OrdenDetailsRepository ordenDetailsRepository, OrdenRepository ordenRepository, ProductoRepository productoRepository) {
        this.ordenDetailsRepository = ordenDetailsRepository;
        this.ordenRepository = ordenRepository;
        this.productoRepository = productoRepository;
    }

    public List<OrdenDetailsResponse> listarDetallesOrden() {
        return ordenDetailsRepository.findAll()
                .stream()
                .map(OrdenDetailsMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OrdenDetailsResponse obtenerPorId(int id) {
        OrdenDetails ordenDetails = ordenDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle de orden no encontrado"));

        return OrdenDetailsMapper.toResponse(ordenDetails);
    }

    @Transactional
    public OrdenDetailsResponse crearOrdenDetalle(OrdenDetailsRequest request) {
        Orden orden = ordenRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada con ID " + request.getOrdenId()));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID " + request.getProductoId()));

        if (producto.getStock() < request.getCantidad()) {
            throw new IllegalArgumentException("No hay suficiente stock disponible.");
        }

        producto.setStock(producto.getStock() - request.getCantidad());
        productoRepository.save(producto);

        OrdenDetails ordenDetails = OrdenDetailsMapper.toEntity(request, orden, producto);
        OrdenDetails ordenDetailsGuardado = ordenDetailsRepository.save(ordenDetails);

        return OrdenDetailsMapper.toResponse(ordenDetailsGuardado);
    }

    @Transactional
    public OrdenDetailsResponse editarOrdenDetalle(OrdenDetailsRequest request) {
        OrdenDetails ordenDetails = ordenDetailsRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Detalle de orden no encontrado"));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID " + request.getProductoId()));

        Orden orden = ordenRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID " + request.getOrdenId()));

        int diferenciaCantidad = request.getCantidad() - ordenDetails.getCantidad();

        if (diferenciaCantidad > 0 && producto.getStock() < diferenciaCantidad) {
            throw new IllegalArgumentException("No hay suficiente stock para aumentar la cantidad.");
        }

        producto.setStock(producto.getStock() - diferenciaCantidad);
        productoRepository.save(producto);

        ordenDetails.setCantidad(request.getCantidad());
        ordenDetails.setPrecioUnitario(request.getPrecioUnitario());
        ordenDetails.setProducto(producto);
        ordenDetails.setOrden(orden);
        ordenDetailsRepository.save(ordenDetails);

        return OrdenDetailsMapper.toResponse(ordenDetails);
    }

    @Transactional
    public void eliminarOrdenDetalle(int id) {
        if (!ordenDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("Detalle de orden no encontrado");
        }

        ordenDetailsRepository.deleteById(id);
    }
}
