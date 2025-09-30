package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.EmpleadoRequest;
import com.ecommerce.app.application.dto.response.EmpleadoResponse;
import com.ecommerce.app.application.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/empleados")
@Tag(name = "Empleados", description = "Operaciones relacionadas con empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de empleados", description = "Devuelve la lista de todos los empleados registrados")
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados() {
        List<EmpleadoResponse> empleados = empleadoService.listarEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Devuelve un empleado según su ID")
    public ResponseEntity<EmpleadoResponse> obtenerPorId(@PathVariable int id) {
        EmpleadoResponse empleado = empleadoService.obtenerPorId(id);
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo empleado", description = "Registra un nuevo empleado en la base de datos")
    public ResponseEntity<EmpleadoResponse> registrarEmpleado(@RequestBody EmpleadoRequest request) {
        EmpleadoResponse empleado = empleadoService.registrarEmpleado(request);
        return ResponseEntity.ok(empleado);
    }

    @PutMapping
    @Operation(summary = "Editar empleado", description = "Actualiza los datos de un empleado existente")
    public ResponseEntity<EmpleadoResponse> editarEmpleado(@RequestBody EmpleadoRequest request) {
        EmpleadoResponse empleado = empleadoService.editarempleado(request);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado no relacionado con usuarios")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable int id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
