package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.TipoProveedorRequest;
import com.ecommerce.app.application.dto.response.TipoProveedorResponse;
import com.ecommerce.app.application.mapper.TipoProveedorMapper;
import com.ecommerce.app.domain.models.TipoProveedor;
import com.ecommerce.app.domain.repository.TipoProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoProveedorService {

    private final TipoProveedorRepository tipoProveedorRepository;

    public TipoProveedorService(TipoProveedorRepository tipoProveedorRepository) {
        this.tipoProveedorRepository = tipoProveedorRepository;
    }

    public List<TipoProveedorResponse> listarTipoProveedores() {
        return tipoProveedorRepository.findAll()
                .stream()
                .map(TipoProveedorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TipoProveedorResponse obtenerPorId(int id) {
        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de proveedor no encontrado con ID: " + id));

        return TipoProveedorMapper.toResponse(tipoProveedor);
    }

    @Transactional
    public TipoProveedorResponse registrarTipoProveedor(TipoProveedorRequest request) {
        Optional<TipoProveedor> tipoProveedorExistente = tipoProveedorRepository.findByNombre(request.nombre());

        if (tipoProveedorExistente.isPresent()) {
            throw new IllegalArgumentException("El tipo de proveedor con el nombre especificado ya existe: " + request.nombre());
        }

        TipoProveedor tipoProveedor = TipoProveedorMapper.toEntity(request);
        tipoProveedor.setId(null);
        TipoProveedor TipoProveedorGuardado = tipoProveedorRepository.save(tipoProveedor);

        return TipoProveedorMapper.toResponse(TipoProveedorGuardado);
    }
    
    @Transactional
    public TipoProveedorResponse editarTipoProveedor(TipoProveedorRequest request) {
        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de proveedor no encontrado con ID: " + request.id()));

        tipoProveedor.setNombre(request.nombre());
        tipoProveedorRepository.save(tipoProveedor);

        return TipoProveedorMapper.toResponse(tipoProveedor);
    }

     @Transactional
    public void eliminarTipoProveedor(int id) {
        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de proveedor no encontrado"));

        if (tipoProveedor.getProveedores() != null && !tipoProveedor.getProveedores().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un tipo de proveedor con proveedores asociados.");
        }

        tipoProveedorRepository.delete(tipoProveedor);
    }
}
