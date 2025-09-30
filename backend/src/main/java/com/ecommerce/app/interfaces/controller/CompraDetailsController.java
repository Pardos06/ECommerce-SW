package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.CompraDetailsRequest;
import com.ecommerce.app.application.dto.response.CompraDetailsResponse;
import com.ecommerce.app.application.service.CompraDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/compra-detalle")
@Tag(name = "Detalle de compra", description = "Operaciones relacionadas con los detalles de compra")
public class CompraDetailsController {

    private final CompraDetailsService compraDetailsService;

    public CompraDetailsController(CompraDetailsService compraDetailsService) {
        this.compraDetailsService = compraDetailsService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de detalle de compras", description = "Devuelve todos los detalles de compras registrados en el sistema")
    public ResponseEntity<List<CompraDetailsResponse>> listarDetallesCompra() {
        List<CompraDetailsResponse> compraDetails = compraDetailsService.listarDetallesCompra();
        return ResponseEntity.ok(compraDetails);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de compra por ID", description = "Devuelve un detalle de compra según su ID")
    public ResponseEntity<CompraDetailsResponse> obtenerPorId(@PathVariable int id) {
        CompraDetailsResponse compraDetails = compraDetailsService.obtenerPorId(id);
        return ResponseEntity.ok(compraDetails);
    }

    @PostMapping
    @Operation(summary = "Registrar detalle de compra", description = "Crea un detalle de compra y descuenta stock del producto")
    public ResponseEntity<CompraDetailsResponse> crearDetalle(@RequestBody CompraDetailsRequest request) {
        CompraDetailsResponse response = compraDetailsService.crearCompraDetalle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Operation(summary = "Editar detalle de compra", description = "Edita un detalle existente y ajusta el stock del producto")
    public ResponseEntity<CompraDetailsResponse> editarDetalle(@RequestBody CompraDetailsRequest request) {
        CompraDetailsResponse response = compraDetailsService.editarCompraDetalle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una compra", description = "Elimina una compra no relacionado con detalles")
    public ResponseEntity<Void> eliminarCompraDetalle(@PathVariable int id) {
        compraDetailsService.eliminarCompraDetalle(id);
        return ResponseEntity.noContent().build();
    }
}
