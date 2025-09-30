package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.CompraRequest;
import com.ecommerce.app.application.dto.response.CompraResponse;
import com.ecommerce.app.application.mapper.CompraMapper;
import com.ecommerce.app.domain.models.Compra;
import com.ecommerce.app.domain.models.Empleado;
import com.ecommerce.app.domain.models.MetodoPago;
import com.ecommerce.app.domain.models.Proveedor;
import com.ecommerce.app.domain.repository.CompraRepository;
import com.ecommerce.app.domain.repository.EmpleadoRepository;
import com.ecommerce.app.domain.repository.MetodoPagoRepository;
import com.ecommerce.app.domain.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final EmpleadoRepository empleadoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ProveedorRepository proveedorRepository;

    public CompraService(CompraRepository compraRepository, EmpleadoRepository empleadoRepository, MetodoPagoRepository metodoPagoRepository, ProveedorRepository proveedorRepository) {
        this.compraRepository = compraRepository;
        this.empleadoRepository = empleadoRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public List<CompraResponse> listarCompras() {
        return compraRepository.findAll()
                .stream()
                .map(CompraMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CompraResponse obtenerPorId(int id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada con ID " + id));

        return CompraMapper.toResponse(compra);
    }

    @Transactional
    public CompraResponse crearCompra(CompraRequest request) {
        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID " + request.getEmpleadoId()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getMetodoPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Método pago no encontrado con ID " + request.getMetodoPagoId()));

        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con ID " + request.getProveedorId()));


        Compra compra = CompraMapper.toEntity(request, metodoPago, proveedor, empleado);
        Compra compraGuardada = compraRepository.save(compra);

        return CompraMapper.toResponse(compraGuardada);
    }

    @Transactional
    public CompraResponse editarCompra(CompraRequest request) {
        Compra compra = compraRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada con ID " + request.getId()));

        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID " + request.getEmpleadoId()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getMetodoPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Método pago no encontrado con ID " + request.getMetodoPagoId()));

        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con ID " + request.getProveedorId()));

        List<String> estadosValidos = List.of("Pendiente", "Pagada", "Cancelada");
        if (!estadosValidos.contains(request.getEstado())) {
            throw new IllegalArgumentException("Estado de compra no válido");
        }

        compra.setFechaCompra(request.getFechaCompra());
        compra.setEstado(request.getEstado());
        compra.setEmpleado(empleado);
        compra.setProveedor(proveedor);
        compra.setMetodoPago(metodoPago);

        compraRepository.save(compra);

        return CompraMapper.toResponse(compra);
    }

    @Transactional
    public void eliminarCompra(int id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compra no encontrada con ID " + id));

        if (compra.getDetails() != null || !compra.getDetails().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una compra asociada a detalles.");
        }

        compraRepository.deleteById(id);
    }
}
