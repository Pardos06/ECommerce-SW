package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.ClienteRequest;
import com.ecommerce.app.application.dto.response.ClienteResponse;
import com.ecommerce.app.application.mapper.ClienteMapper;
import com.ecommerce.app.domain.models.Cliente;
import com.ecommerce.app.domain.models.Usuario;
import com.ecommerce.app.domain.repository.ClienteRepository;
import com.ecommerce.app.domain.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ClienteResponse> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse obtenerPorId(int id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID " + id));

        return ClienteMapper.toResponse(cliente);
    }

    @Transactional
    public ClienteResponse registrarCliente(ClienteRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        boolean clienteYaExiste = clienteRepository.findByUsuario(usuario).isPresent();
        if (clienteYaExiste) {
            throw new IllegalArgumentException("Este usuario ya está asociado a un cliente.");
        }

        Cliente cliente = ClienteMapper.toEntity(request, usuario);
        cliente.setId(null);
        Cliente clienteGuardado = clienteRepository.save(cliente);

        return ClienteMapper.toResponse(clienteGuardado);
    }

    @Transactional
    public ClienteResponse editarCliente(ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID " + request.id()));

        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        cliente.setUsuario(usuario);
        cliente.setTelefono(request.telefono());
        cliente.setDireccion(request.direccion());

        clienteRepository.save(cliente);

        return ClienteMapper.toResponse(cliente);
    }

    @Transactional
    public void eliminarCliente(int id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        if (cliente.getOrdenes() != null && !cliente.getOrdenes().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un cliente asociado a una orden");
        }

        clienteRepository.delete(cliente);
    }
}
