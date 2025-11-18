package com.ecommerce.app.application.service;

import com.ecommerce.app.application.dto.request.AuthRequest;
import com.ecommerce.app.application.dto.request.RegistrarClienteRequest;
import com.ecommerce.app.application.dto.response.AuthResponse;
import com.ecommerce.app.domain.models.Cliente;
import com.ecommerce.app.domain.models.Rol;
import com.ecommerce.app.domain.models.Usuario;
import com.ecommerce.app.domain.repository.ClienteRepository;
import com.ecommerce.app.domain.repository.RolRepository;
import com.ecommerce.app.domain.repository.UsuarioRepository;
import com.ecommerce.app.infrastructure.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final ClienteRepository clienteRepository;

    public AuthService(AuthenticationManager authManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RolRepository rolRepository, ClienteRepository clienteRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.clienteRepository = clienteRepository;
    }

    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.contrasena()
                )
        );

        String token = jwtUtil.generateToken(request.email());

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new AuthResponse(token, usuario.getRol().getNombre(), usuario.getNombre());
    }

    @Transactional
    public AuthResponse registrarCliente(RegistrarClienteRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado: " + request.email());
        }

        Rol rolCliente = rolRepository.findByNombre("Cliente")
                .orElseThrow(() -> new IllegalStateException("Rol 'Cliente' no encontrado"));

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.nombre());
        nuevo.setEmail(request.email());
        nuevo.setPasswordHash(passwordEncoder.encode(request.contrasena()));
        nuevo.setEstado("Activo");
        nuevo.setRol(rolCliente);

        usuarioRepository.save(nuevo);

        Cliente cliente = new Cliente();
        cliente.setTelefono(request.telefono());
        cliente.setDireccion(request.direccion());
        cliente.setUsuario(nuevo);

        clienteRepository.save(cliente);

        String token = jwtUtil.generateToken(nuevo.getEmail());

        return new AuthResponse(token, nuevo.getRol().getNombre(), nuevo.getNombre());
    }
}
