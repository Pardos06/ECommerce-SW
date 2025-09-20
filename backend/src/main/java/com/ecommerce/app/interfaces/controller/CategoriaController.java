package com.ecommerce.app.interfaces.controller;

import java.util.List;

import com.ecommerce.app.application.dto.request.CategoriaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.application.dto.response.CategoriaResponse;
import com.ecommerce.app.application.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/categorias")
@Tag(name = "Categoría", description = "Operaciones relacionadas con las categorías de productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de categorías", description = "Devuelve todas las cateogrías de productos disponibles")
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        List<CategoriaResponse> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")    
    @Operation(summary = "Obtener categoría por ID", description = "Devuelve una categoría específica según su ID")
    public ResponseEntity<CategoriaResponse> obtenerPorId(@PathVariable int id) {
        CategoriaResponse categoria = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva categoría", description = "Registra una categoría en la base de datos")
    public ResponseEntity<CategoriaResponse> registrarCategoria(@RequestBody CategoriaRequest request) {
        CategoriaResponse categoria = categoriaService.registrarCategoria(request);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping
    @Operation(summary = "Editar categoría", description = "Actualiza los datos de una categoría existente")
    public ResponseEntity<CategoriaResponse> editarCategoria(@RequestBody CategoriaRequest request) {
        CategoriaResponse categoria = categoriaService.editarCategoria(request);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría no relacionada con productos")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
    
}
