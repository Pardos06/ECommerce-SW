package com.ecommerce.app.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.request.TipoProveedorRequest;
import com.ecommerce.app.application.dto.response.TipoProveedorResponse;
import com.ecommerce.app.application.mapper.TipoProveedorMapper;
import com.ecommerce.app.domain.models.TipoProveedor;
import com.ecommerce.app.domain.repository.TipoProveedorRepository;

import jakarta.persistence.EntityNotFoundException;

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

    public TipoProveedorResponse registrarTipoProveedor(TipoProveedorRequest request) {
        Optional<TipoProveedor> tipoProveedorExistente = tipoProveedorRepository.findByNombre(request.getNombre());

        if (tipoProveedorExistente.isPresent()) {
            throw new IllegalArgumentException("El tipo de proveedor con el nombre especificado ya existe: " + request.getNombre());
        }

        TipoProveedor tipoProveedor = TipoProveedorMapper.toEntity(request);
        TipoProveedor TipoProveedorGuardado = tipoProveedorRepository.save(tipoProveedor);

        return TipoProveedorMapper.toResponse(TipoProveedorGuardado);
    }

    public TipoProveedorResponse editarTipoProveedor(TipoProveedorRequest request) {
        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de proveedor no encontrado con ID: " + request.getId()));

        tipoProveedor.setNombre(request.getNombre());
        tipoProveedorRepository.save(tipoProveedor);

        return TipoProveedorMapper.toResponse(tipoProveedor);
    }

    public void eliminarTipoProveedor(int id) {
        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de proveedor no encontrado"));

        if (tipoProveedor.getProveedores() != null && !tipoProveedor.getProveedores().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un tipo de proveedor con proveedores asociados.");
        }

        tipoProveedorRepository.delete(tipoProveedor);
    }

    public List<TipoProveedorResponse> buscarPorNombre(String nombre) {
        return tipoProveedorRepository.findByNombreContainingIgnoreCase(nombre)
        		.stream()
        		.map(TipoProveedorMapper::toResponse)
        		.toList();
    }
}
