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

    public ProveedorResponse registrarProveedor(ProveedorRequest request) {
        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(request.getTipoProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de proveedor especificado no existe"));

        Proveedor proveedor = ProveedorMapper.toEntity(request, tipoProveedor);
        Proveedor proveedorGuardado = proveedorRepository.save((proveedor));

        return ProveedorMapper.toResponse(proveedorGuardado);
    }

    public ProveedorResponse editarProveedor(ProveedorRequest request) {
        Proveedor proveedor = proveedorRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado con id: " + request.getId()));

        TipoProveedor tipoProveedor = tipoProveedorRepository.findById(request.getTipoProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de proveedor especificado no existe: " + request.getTipoProveedorId()));

        proveedor.setNombre(request.getNombre());
        proveedor.setEmail(request.getEmail());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
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
