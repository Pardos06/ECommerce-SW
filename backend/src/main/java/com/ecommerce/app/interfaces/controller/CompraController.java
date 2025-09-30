package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.CompraRequest;
import com.ecommerce.app.application.dto.response.CompraResponse;
import com.ecommerce.app.application.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/compras")
@Tag(name = "Compra", description = "Operaciones relacionadas con compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de compras", description = "Devuelve todas las compras registradas en el sistema")
    public ResponseEntity<List<CompraResponse>> listarCompras() {
        List<CompraResponse> compras = compraService.listarCompras();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener compra por ID", description = "Devuelve una compra según su ID")
    public ResponseEntity<CompraResponse> obtenerPorId(@PathVariable int id) {
        CompraResponse compra = compraService.obtenerPorId(id);
        return ResponseEntity.ok(compra);
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva compra", description = "Registra una nueva compra en la base de datos")
    public ResponseEntity<CompraResponse> crearCompra(@RequestBody CompraRequest request) {
        CompraResponse compra = compraService.crearCompra(request);
        return ResponseEntity.ok(compra);
    }

    @PutMapping
    @Operation(summary = "Editar compra", description = "Actualiza los datos de una compra existente")
    public ResponseEntity<CompraResponse> editarCompra(@RequestBody CompraRequest request) {
        CompraResponse compra = compraService.editarCompra(request);
        return ResponseEntity.ok(compra);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    @Operation(summary = "Eliminar una compra", description = "Elimina una compra no relacionada con detalles")
    public ResponseEntity<Void> eliminarCompra(@PathVariable int id) {
        compraService.eliminarCompra(id);
        return ResponseEntity.noContent().build();
    }
}