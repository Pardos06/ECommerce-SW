package com.ecommerce.app.interfaces.controller;

import java.util.List;

import com.ecommerce.app.application.dto.request.TipoProveedorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.application.dto.response.TipoProveedorResponse;
import com.ecommerce.app.application.service.TipoProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/tipos-proveedor")
@Tag(name = "TipoProveedor", description = "Operaciones relacionadas con los tipos de proveedor")
public class TipoProveedorController {

    private final TipoProveedorService tipoProveedorService;

    public TipoProveedorController(TipoProveedorService tipoProveedorService) {
        this.tipoProveedorService = tipoProveedorService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de tipos de proveedor", description = "Devuelve todos los tipos de proveedor disponibles")
    public ResponseEntity<List<TipoProveedorResponse>> listarTiposProveedor() {
        List<TipoProveedorResponse> tiposProveedor = tipoProveedorService.listarTipoProveedores();
        return ResponseEntity.ok(tiposProveedor);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de proveedor por ID", description = "Devuelve un tipo de proveedor específico según su ID")
    public ResponseEntity<TipoProveedorResponse> obtenerPorId(@PathVariable int id) {
        TipoProveedorResponse tipoProveedor = tipoProveedorService.obtenerPorId(id);
        return ResponseEntity.ok(tipoProveedor);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo tipo de proveedor", description = "Registra un tipo de proveedor en la base de datos")
    public ResponseEntity<TipoProveedorResponse> registrarTipoProveedor(@RequestBody TipoProveedorRequest request) {
        TipoProveedorResponse tipoProveedor = tipoProveedorService.registrarTipoProveedor(request);
        return ResponseEntity.ok(tipoProveedor);
    }

    @PutMapping
    @Operation(summary = "Editar tipo de proveedor", description = "Actualiza los datos de un tipo de proveedor existente")
    public ResponseEntity<TipoProveedorResponse> editarTipoProveedor(@RequestBody TipoProveedorRequest request) {
        TipoProveedorResponse tipoProveedor = tipoProveedorService.editarTipoProveedor(request);
        return ResponseEntity.ok(tipoProveedor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de proveedor", description = "Elimina un tipo de proveedor no relacionado con proveedores")
    public ResponseEntity<Void> eliminarTipoProveedor(@PathVariable int id) {
        tipoProveedorService.eliminarTipoProveedor(id);
        return ResponseEntity.noContent().build();
    }

}