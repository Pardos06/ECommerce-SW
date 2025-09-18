package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.AuthRequest;
import com.ecommerce.app.application.dto.response.AuthResponse;
import com.ecommerce.app.domain.models.Usuario;
import com.ecommerce.app.domain.repository.UsuarioRepository;
import com.ecommerce.app.infrastructure.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthService(AuthenticationManager authManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getContrasena()
                )
        );

        String token = jwtUtil.generateToken(request.getEmail());

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new AuthResponse(token, usuario.getRol().getNombre(), usuario.getNombre());
    }
}
