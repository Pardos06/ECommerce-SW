package com.ecommerce.app.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.request.UsuarioRequest;
import com.ecommerce.app.application.dto.response.UsuarioResponse;
import com.ecommerce.app.application.mapper.UsuarioMapper;
import com.ecommerce.app.domain.models.Rol;
import com.ecommerce.app.domain.models.Usuario;
import com.ecommerce.app.domain.repository.RolRepository;
import com.ecommerce.app.domain.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse obtenerPorId(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID " + id));

        return UsuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse registrarUsuario(UsuarioRequest request) {
        Rol rol = rolRepository.findById(request.rolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no válido"));

        Usuario usuario = new Usuario();
        usuario.setId(null);
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
        usuario.setEstado(request.estado());
        usuario.setRol(rol);

        usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse editarUsuario(UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        if (request.passwordHash() != null && !request.passwordHash().isBlank()) {
            usuario.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
        }
        usuario.setEstado(request.estado());

        Rol rol = rolRepository.findById(request.rolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no válido"));
        usuario.setRol(rol);

        usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    @Transactional
    public void eliminarUsuario(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (usuario.getCliente() != null) {
            throw new IllegalStateException("No se puede eliminar un usuario asociado a un cliente");
        }

        usuarioRepository.delete(usuario);
    }
}
