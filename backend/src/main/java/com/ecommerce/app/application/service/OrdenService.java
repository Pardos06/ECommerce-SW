package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.OrdenRequest;
import com.ecommerce.app.application.dto.response.OrdenResponse;
import com.ecommerce.app.application.mapper.OrdenMapper;
import com.ecommerce.app.domain.models.Cliente;
import com.ecommerce.app.domain.models.MetodoPago;
import com.ecommerce.app.domain.models.Orden;
import com.ecommerce.app.domain.repository.ClienteRepository;
import com.ecommerce.app.domain.repository.MetodoPagoRepository;
import com.ecommerce.app.domain.repository.OrdenRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ClienteRepository clienteRepository;
    private final MetodoPagoRepository metodoPagoRepository;

    public OrdenService(OrdenRepository ordenRepository, ClienteRepository clienteRepository, MetodoPagoRepository metodoPagoRepository) {
        this.ordenRepository = ordenRepository;
        this.clienteRepository = clienteRepository;
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<OrdenResponse> listarOrdenes() {
        return ordenRepository.findAll()
                .stream()
                .map(OrdenMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OrdenResponse obtenerPorId(int id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID " + id));

        return OrdenMapper.toResponse(orden);
    }

    @Transactional
    public OrdenResponse crearOrden(OrdenRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID " + request.clienteId()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.metodoPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Método pago no encontrado con ID " + request.metodoPagoId()));

        Orden orden = OrdenMapper.toEntity(request, cliente, metodoPago);
        orden.setId(null);
        Orden ordenGuardada = ordenRepository.save(orden);

        return OrdenMapper.toResponse(ordenGuardada);
    }

    @Transactional
    public OrdenResponse editarOrden(OrdenRequest request) {
        Orden orden = ordenRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID " + request.id()));

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID " + request.clienteId()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.metodoPagoId())
                .orElseThrow(() -> new IllegalArgumentException("Método pago no encontrado con ID " + request.metodoPagoId()));

        orden.setFechaOrden(request.fechaOrden());
        orden.setEstado(request.estado());
        orden.setEstadoEmail(request.estado());
        orden.setCliente(cliente);
        orden.setMetodoPago(metodoPago);

        ordenRepository.save(orden);

        return OrdenMapper.toResponse(orden);
    }

    @Transactional
    public void eliminarOrden(int id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID " + id));

        if (orden.getDetails() != null || !orden.getDetails().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una orden asociada a detalles.");
        }

        ordenRepository.deleteById(id);
    }

    public List<OrdenResponse> obtenerOrdenPorCliente(int clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID " + clienteId));

        List<Orden> ordenes = ordenRepository.findByCliente(cliente);

        return ordenes.stream()
                .map(OrdenMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrdenResponse actualizarEstado(int id, String nuevoEstado) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID " + id));

        if ("Completado".equalsIgnoreCase(orden.getEstado())) {
            throw new IllegalArgumentException("No se puede modificar una orden ya completada.");
        }

        orden.setEstado(nuevoEstado);
        ordenRepository.save(orden);

        return OrdenMapper.toResponse(orden);
    }
}
