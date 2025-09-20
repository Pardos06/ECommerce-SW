package com.ecommerce.app.interfaces.controller;

import com.ecommerce.app.application.dto.request.AuthRequest;
import com.ecommerce.app.application.dto.request.RegistrarClienteRequest;
import com.ecommerce.app.application.dto.response.AuthResponse;
import com.ecommerce.app.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Autenticación", description = "Endpoints para autenticación con JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y devuelve un JWT válido")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo cliente", description = "Registra un cliente en el sistema creando su usuario y datos")
    public ResponseEntity<AuthResponse>  registrarCliente(@RequestBody RegistrarClienteRequest request) {
        return ResponseEntity.ok(authService.registrarCliente(request));
    }
}
