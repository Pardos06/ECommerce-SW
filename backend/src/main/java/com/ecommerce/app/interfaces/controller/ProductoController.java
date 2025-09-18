package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.ProductoRequest;
import com.ecommerce.app.application.dto.response.ApiErrorResponse;
import com.ecommerce.app.application.dto.response.ProductoResponse;
import com.ecommerce.app.application.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener lista de productos", description = "Devuelve todos los productos registrados en el sistema")
    public ResponseEntity<List<ProductoResponse>> listarProductos() {
        List<ProductoResponse> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve un producto según su identificador único")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        ProductoResponse producto = productoService.obtenerPorId(id);

        if (producto == null) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiErrorResponse(404, "Producto no encontrado"));
        }

        return ResponseEntity.ok(producto);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo producto", description = "Registra un nuevo producto en la base de datos")
    public ResponseEntity<?> registrarProducto(@RequestBody ProductoRequest request) {
        ProductoResponse producto = productoService.registrarProducto(request);

        if (producto == null) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(404, "La categoría especificada no existe."));
        }

        return ResponseEntity.ok(producto);
    }

    @PutMapping
    @Operation(summary = "Editar producto", description = "Actualiza los datos de un producto existente")
    public ResponseEntity<?> editarProducto(@RequestBody ProductoRequest request) {
        ProductoResponse producto = productoService.editarProducto((request));

        if (producto == null) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiErrorResponse(404, "Producto no encontrado"));
        }

        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto no relacionado con compras u órdenes")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(404, "Producto no encontrado"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse(400, "No se puede eliminar el producto porque está relacionado."));
        }
    }

}
