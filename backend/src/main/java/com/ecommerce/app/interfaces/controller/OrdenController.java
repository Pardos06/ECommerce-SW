package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.OrdenCompletaRequest;
import com.ecommerce.app.application.dto.request.OrdenRequest;
import com.ecommerce.app.application.dto.response.OrdenResponse;
import com.ecommerce.app.application.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ordenes")
@Tag(name = "Orden", description = "Operaciones relacionadas con órdenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener lista de órdenes", description = "Devuelve todas las órdenes registradas en el sistema")
    public ResponseEntity<List<OrdenResponse>> listarOrdenes() {
        List<OrdenResponse> ordenes = ordenService.listarOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener orden por ID", description = "Devuelve una orden según su ID")
    public ResponseEntity<OrdenResponse> obtenerPorId(@PathVariable int id) {
        OrdenResponse orden = ordenService.obtenerPorId(id);
        return ResponseEntity.ok(orden);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Administrador', 'Cliente')")
    @Operation(summary = "Registrar una nueva orden completa", description = "Registra una orden junto con sus detalles en una sola transacción")
    public ResponseEntity<OrdenResponse> crearOrdenCompleta(@RequestBody OrdenCompletaRequest request) {
        OrdenResponse orden = ordenService.crearOrdenCompleta(request);
        return ResponseEntity.ok(orden);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Editar orden", description = "Actualiza los datos de una orden existente")
    public ResponseEntity<OrdenResponse> editarOrden(@RequestBody OrdenRequest request) {
        OrdenResponse orden = ordenService.editarOrden(request);
        return ResponseEntity.ok(orden);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Eliminar una orden", description = "Elimina una orden no relacionada con detalles")
    public ResponseEntity<Void> eliminarOrden(@PathVariable int id) {
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-cliente/{clienteId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrdenResponse>> listarOrdenesPorCliente(@PathVariable int clienteId) {
        List<OrdenResponse> ordenes = ordenService.obtenerOrdenPorCliente(clienteId);
        return ResponseEntity.ok(ordenes);
    }

    @PutMapping("/actualizar-estado/{id}")
    @PreAuthorize("hasAuthority('Administrador')")
    @Operation(summary = "Actualizar estado de una orden", description = "Actualiza el estado de una orden existente")
    public ResponseEntity<OrdenResponse> actualizarEstado(@PathVariable int id, @RequestBody String nuevoEstado) {
        OrdenResponse orden = ordenService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(orden);
    }
}
