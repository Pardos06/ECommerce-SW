package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.RolRequest;
import com.ecommerce.app.application.dto.response.RolResponse;
import com.ecommerce.app.application.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
@Tag(name = "Rol", description = "Operaciones relacionadas con los roles de usuarios")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de roles", description = "Devuelve todos los roles de usuarios disponibles")
    public ResponseEntity<List<RolResponse>> listarRoles() {
        List<RolResponse> roles = rolService.listarRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Devuelve un rol específico según su ID")
    public ResponseEntity<RolResponse> obtenerPorId(@PathVariable int id) {
        RolResponse rol = rolService.obtenerPorId(id);
        return ResponseEntity.ok(rol);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Registrar un nuevo rol", description = "Registra un rol en la base de datos")
    public ResponseEntity<RolResponse> registrarRol(@RequestBody RolRequest request) {
        RolResponse rol = rolService.registrarRol(request);
        return ResponseEntity.ok(rol);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Editar rol", description = "Actualiza los datos de un rol existente")
    public ResponseEntity<RolResponse> editarRol(@RequestBody RolRequest request) {
        RolResponse rol = rolService.editarRol(request);
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Eliminar un rol", description = "Elimina un rol no relacionado con usuarios")
    public ResponseEntity<Void> eliminarRol(@PathVariable int id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}