package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.OrdenCompletaRequest;
import com.ecommerce.app.application.dto.request.OrdenDetailsRequest;
import com.ecommerce.app.application.dto.request.OrdenRequest;
import com.ecommerce.app.application.dto.response.OrdenResponse;
import com.ecommerce.app.application.mapper.OrdenMapper;
import com.ecommerce.app.domain.models.Cliente;
import com.ecommerce.app.domain.models.MetodoPago;
import com.ecommerce.app.domain.models.Orden;
import com.ecommerce.app.domain.models.OrdenDetails;
import com.ecommerce.app.domain.models.Producto;
import com.ecommerce.app.domain.repository.ClienteRepository;
import com.ecommerce.app.domain.repository.MetodoPagoRepository;
import com.ecommerce.app.domain.repository.OrdenDetailsRepository;
import com.ecommerce.app.domain.repository.OrdenRepository;
import com.ecommerce.app.domain.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final OrdenDetailsRepository ordenDetailsRepository;
    private final ClienteRepository clienteRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ProductoRepository productoRepository;

    public OrdenService(
            OrdenRepository ordenRepository,
            OrdenDetailsRepository ordenDetailsRepository,
            ClienteRepository clienteRepository,
            MetodoPagoRepository metodoPagoRepository,
            ProductoRepository productoRepository
    ) {
        this.ordenRepository = ordenRepository;
        this.ordenDetailsRepository = ordenDetailsRepository;
        this.clienteRepository = clienteRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public OrdenResponse crearOrdenCompleta(OrdenCompletaRequest request) {
        OrdenRequest ordenReq = request.orden();
        List<OrdenDetailsRequest> detallesReq = request.detalles();

        Cliente cliente = clienteRepository.findById(ordenReq.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID " + ordenReq.clienteId()));

        MetodoPago metodoPago = metodoPagoRepository.findById(ordenReq.metodoPagoId())
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado con ID " + ordenReq.metodoPagoId()));

        Orden orden = OrdenMapper.toEntity(ordenReq, cliente, metodoPago);
        orden.setId(null);
        Orden ordenGuardada = ordenRepository.save(orden);

        for (OrdenDetailsRequest d : detallesReq) {
            Producto producto = productoRepository.findById(d.productoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID " + d.productoId()));

            OrdenDetails detalle = new OrdenDetails();
            detalle.setOrden(ordenGuardada);
            detalle.setProducto(producto);
            detalle.setCantidad(d.cantidad());
            detalle.setPrecioUnitario(d.precioUnitario());
            ordenDetailsRepository.save(detalle);

            if (producto.getStock() < d.cantidad()) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto ID " + d.productoId());
            }
            producto.setStock(producto.getStock() - d.cantidad());
            productoRepository.save(producto);
        }

        return OrdenMapper.toResponse(ordenGuardada);
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
        orden.setEstadoEmail(request.estadoEmail());
        orden.setCliente(cliente);
        orden.setMetodoPago(metodoPago);

        ordenRepository.save(orden);
        return OrdenMapper.toResponse(orden);
    }

    @Transactional
    public void eliminarOrden(int id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID " + id));

        if (orden.getDetails() != null && !orden.getDetails().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una orden asociada a detalles.");
        }

        ordenRepository.deleteById(id);
    }

    public List<OrdenResponse> obtenerOrdenPorCliente(int clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID " + clienteId));

        return ordenRepository.findByCliente(cliente)
                .stream()
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
