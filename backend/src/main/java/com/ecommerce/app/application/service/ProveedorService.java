package com.ecommerce.app.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.request.ProveedorRequest;
import com.ecommerce.app.application.dto.response.ProveedorResponse;
import com.ecommerce.app.application.mapper.ProveedorMapper;
import com.ecommerce.app.domain.models.Proveedor;
import com.ecommerce.app.domain.models.TipoProveedor;
import com.ecommerce.app.domain.repository.ProveedorRepository;
import com.ecommerce.app.domain.repository.TipoProveedorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final TipoProveedorRepository tipoProveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository, TipoProveedorRepository tipoProveedorRepository) {
        this.proveedorRepository = proveedorRepository;
        this.tipoProveedorRepository = tipoProveedorRepository;
    }

    public List<ProveedorResponse> listarProveedoress() {
        return proveedorRepository.findAll()
                .stream()
                .map(ProveedorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProveedorResponse obtenerPorId(int id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("proveedor no encontrado con id: " + id));

        return ProveedorMapper.toResponse(proveedor);
    }

    @Transactional
    public ProveedorResponse registrarProveedor(ProveedorRequest request) {
        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(request.tipoProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de proveedor especificado no existe"));

        Proveedor proveedor = ProveedorMapper.toEntity(request, tipoProveedor);
        proveedor.setId(null);
        Proveedor proveedorGuardado = proveedorRepository.save((proveedor));

        return ProveedorMapper.toResponse(proveedorGuardado);
    }

    @Transactional
    public ProveedorResponse editarProveedor(ProveedorRequest request) {
        Proveedor proveedor = proveedorRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con id: " + request.id()));

        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(request.tipoProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de proveedor especificado no existe: " + request.tipoProveedorId()));

        proveedor.setNombre(request.nombre());
        proveedor.setEmail(request.email());
        proveedor.setTelefono(request.telefono());
        proveedor.setDireccion(request.direccion());
        proveedor.setTipoProveedor(tipoProveedor);

        proveedorRepository.save(proveedor);

        return ProveedorMapper.toResponse(proveedor);
    }

    @Transactional
    public void eliminarProveedor(int id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        if (proveedor.getCompras() != null && !proveedor.getCompras().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un proveedor asociado a una compra.");
        }

        proveedorRepository.delete(proveedor);
    }
    
    public List<ProveedorResponse> buscarPorTipoProveedor(String tipoNombre) {
    	return proveedorRepository.buscarPorTipoProveedor(tipoNombre)
    			.stream()
    			.map(ProveedorMapper::toResponse)
    			.toList();
    }
}
