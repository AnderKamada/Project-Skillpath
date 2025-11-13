package com.fiap.gs.api.controller;

import com.fiap.gs.api.domain.Usuario;
import com.fiap.gs.api.repository.UsuarioRepository;
import com.fiap.gs.api.security.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {

       // username = email
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(req.username());

        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = optUsuario.get();

        boolean senhaOk = passwordEncoder.matches(req.password(), usuario.getSenha());
        if (!senhaOk) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.generateToken(usuario.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
    }

    public record LoginRequest(@NotBlank String username,
                               @NotBlank String password) {}

    public record LoginResponse(String accessToken,
                                String tokenType) {}
}
