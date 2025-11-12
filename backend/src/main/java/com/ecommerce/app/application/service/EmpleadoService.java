package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.EmpleadoRequest;
import com.ecommerce.app.application.dto.response.EmpleadoResponse;
import com.ecommerce.app.application.mapper.EmpleadoMapper;
import com.ecommerce.app.domain.models.Empleado;
import com.ecommerce.app.domain.models.Usuario;
import com.ecommerce.app.domain.repository.EmpleadoRepository;
import com.ecommerce.app.domain.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<EmpleadoResponse> listarEmpleados() {
        return empleadoRepository.findAll()
                .stream()
                .map(EmpleadoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EmpleadoResponse obtenerPorId(int id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID " + id));

        return EmpleadoMapper.toResponse(empleado);
    }

    @Transactional
    public EmpleadoResponse registrarEmpleado(EmpleadoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        boolean empleadoExiste = empleadoRepository.findByUsuario(usuario).isPresent();

        if (empleadoExiste) {
            throw new IllegalArgumentException("Este usuario ya está registrado como empleado.");
        }

        Empleado empleado = EmpleadoMapper.toEntity(request, usuario);
        empleado.setId(null);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);

        return EmpleadoMapper.toResponse(empleadoGuardado);
    }

    @Transactional
    public EmpleadoResponse editarempleado(EmpleadoRequest request) {
        Empleado empleado = empleadoRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID " + request.id()));

        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        empleado.setUsuario(usuario);
        empleado.setArea(request.area());
        empleado.setCargo(request.cargo());

        empleadoRepository.save(empleado);

        return EmpleadoMapper.toResponse(empleado);
    }

    @Transactional
    public void eliminarEmpleado(int id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado."));

        empleadoRepository.delete(empleado);
    }
}
