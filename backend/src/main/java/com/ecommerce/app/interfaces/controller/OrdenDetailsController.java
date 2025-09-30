package com.ecommerce.app.interfaces.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.application.dto.request.OrdenDetailsRequest;
import com.ecommerce.app.application.dto.response.OrdenDetailsResponse;
import com.ecommerce.app.application.service.OrdenDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/orden-detalle")
@Tag(name = "Detalle de orden", description = "Operaciones relacionadas con los detalles de orden")
public class OrdenDetailsController {

    private final OrdenDetailsService ordenDetailsService;

    public OrdenDetailsController(OrdenDetailsService ordenDetailsService) {
        this.ordenDetailsService = ordenDetailsService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de detalle de órdenes", description = "Devuelve todos los detalles de órdenes registrados en el sistema")
    public ResponseEntity<List<OrdenDetailsResponse>> listarDetallesOrden() {
        List<OrdenDetailsResponse> ordenDetails = ordenDetailsService.listarDetallesOrden();
        return ResponseEntity.ok(ordenDetails);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de orden por ID", description = "Devuelve un detalle de orden según su ID")
    public ResponseEntity<OrdenDetailsResponse> obtenerPorId(@PathVariable int id) {
        OrdenDetailsResponse ordenDetails = ordenDetailsService.obtenerPorId(id);
        return ResponseEntity.ok(ordenDetails);
    }

    @PostMapping
    @Operation(summary = "Registrar detalle de orden", description = "Crea un detalle de orden y descuenta stock del producto")
    public ResponseEntity<OrdenDetailsResponse> crearDetalle(@RequestBody OrdenDetailsRequest request) {
        OrdenDetailsResponse response = ordenDetailsService.crearOrdenDetalle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Operation(summary = "Editar detalle de orden", description = "Edita un detalle existente y ajusta el stock del producto")
    public ResponseEntity<OrdenDetailsResponse> editarDetalle(@RequestBody OrdenDetailsRequest request) {
        OrdenDetailsResponse response = ordenDetailsService.editarOrdenDetalle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una orden", description = "Elimina una orden no relacionado con detalles")
    public ResponseEntity<Void> eliminarOrdenDetalle(@PathVariable int id) {
        ordenDetailsService.eliminarOrdenDetalle(id);
        return ResponseEntity.noContent().build();
    }
}
