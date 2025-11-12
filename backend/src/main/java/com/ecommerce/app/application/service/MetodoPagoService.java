package com.ecommerce.app.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.app.application.dto.request.MetodoPagoRequest;
import com.ecommerce.app.application.mapper.MetodoPagoMapper;
import com.ecommerce.app.domain.models.MetodoPago;
import com.ecommerce.app.domain.repository.MetodoPagoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.response.MetodoPagoResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoService(MetodoPagoRepository metogoPagoRepository) {
        this.metodoPagoRepository = metogoPagoRepository;
    }

    public List<MetodoPagoResponse> listarMetodosPago() {
        return metodoPagoRepository.findAll()
                .stream()
                .map(MetodoPagoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public MetodoPagoResponse obtenerPorId(int id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado con ID: " + id));

        return MetodoPagoMapper.toResponse(metodoPago);
    }
    
    @Transactional
    public MetodoPagoResponse registrarNetodoPago(MetodoPagoRequest request) {
        Optional<MetodoPago> metodoPagoExistente = metodoPagoRepository.findByNombre(request.nombre());

        if (metodoPagoExistente.isPresent()) {
            throw new IllegalArgumentException("El método de pago con el nombre especificado ya existe: " + request.nombre());
        }

        MetodoPago metodoPago = MetodoPagoMapper.toEntity(request);
        metodoPago.setId(null);
        MetodoPago metodoPagoGuardada = metodoPagoRepository.save(metodoPago);

        return MetodoPagoMapper.toResponse(metodoPagoGuardada);
    }

    @Transactional
    public MetodoPagoResponse editarMetodoPago(MetodoPagoRequest request) {
        MetodoPago metodoPago = metodoPagoRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado con ID: " + request.id()));

        metodoPago.setNombre(request.nombre());

        metodoPagoRepository.save(metodoPago);

        return MetodoPagoMapper.toResponse(metodoPago);
    }

    @Transactional
    public void eliminarMetodoPago(int id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado"));

        if (metodoPago.getCompras() != null && !metodoPago.getCompras().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un método de pago asociado a compras.");
        }

        if (metodoPago.getOrdenes() != null && !metodoPago.getOrdenes().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un método de pago asociado a órdenes.");
        }

        metodoPagoRepository.delete(metodoPago);
    }

    public List<MetodoPagoResponse> buscarPorNombre(String nombre) {
        return metodoPagoRepository.findByNombreContainingIgnoreCase(nombre)
        		.stream()
                .map(MetodoPagoMapper::toResponse)
                .toList();
    }
}
