package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.ClienteRequest;
import com.ecommerce.app.application.dto.response.ClienteResponse;
import com.ecommerce.app.application.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de clientes", description = "Devuelve la lista de todos los clientes registrados")
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        List<ClienteResponse> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Devuelve un cliente según su ID")
    public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable int id) {
        ClienteResponse cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo cliente", description = "Registra un nuevo cliente en la base de datos")
    public ResponseEntity<ClienteResponse> registrarCliente(@RequestBody ClienteRequest request) {
        ClienteResponse cliente = clienteService.registrarCliente(request);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping
    @Operation(summary = "Editar cliente", description = "Actualiza los datos de un cliente existente")
    public ResponseEntity<ClienteResponse> editarCliente(@RequestBody ClienteRequest request) {
        ClienteResponse cliente = clienteService.editarCliente(request);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente no relacionado con usuarios")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
