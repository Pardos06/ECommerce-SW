package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.RolRequest;
import com.ecommerce.app.application.dto.response.RolResponse;
import com.ecommerce.app.application.mapper.RolMapper;
import com.ecommerce.app.domain.models.Rol;
import com.ecommerce.app.domain.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<RolResponse> listarRoles() {
        return rolRepository.findAll()
                .stream()
                .map(RolMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RolResponse obtenerPorId(int id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + id));

        return RolMapper.toResponse(rol);
    }

    public RolResponse registrarRol(RolRequest request) {
        Optional<Rol> rolExistente = rolRepository.findByNombre(request.getNombre());

        if (rolExistente.isPresent()) {
            throw new IllegalArgumentException("El rol con el nombre especificado ya existe: " + request.getNombre());
        }

        Rol rol = RolMapper.toEntity(request);
        Rol rolGuardada = rolRepository.save(rol);

        return RolMapper.toResponse(rolGuardada);
    }

    public RolResponse editarRol(RolRequest request) {
        Rol rol = rolRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + request.getId()));

        rol.setNombre(request.getNombre());

        rolRepository.save(rol);

        return RolMapper.toResponse(rol);
    }

    public void eliminarRol(int id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        if (rol.getUsuarios() != null && !rol.getUsuarios().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un rol asociado a un usuario");
        }

        rolRepository.delete(rol);
    }
}
