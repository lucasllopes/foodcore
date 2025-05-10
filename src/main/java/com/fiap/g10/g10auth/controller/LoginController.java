package com.fiap.g10.g10auth.controller;

import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.dto.LoginRequest;
import com.fiap.g10.g10auth.infrastructure.UsuarioDetailsAdapter;
import com.fiap.g10.g10auth.service.TokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public LoginController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {

        logger.info("Request para login");

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var usuarioDetails = (UsuarioDetailsAdapter) authentication.getPrincipal();

        String token = tokenService.geraToken(usuarioDetails);
        return ResponseEntity.ok(token);
    }
}