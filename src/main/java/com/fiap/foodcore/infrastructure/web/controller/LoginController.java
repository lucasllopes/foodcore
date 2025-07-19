package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.infrastructure.web.controller.dto.LoginRequestDTO;
import com.fiap.foodcore.infrastructure.security.UserDetailsAdapter;
import com.fiap.foodcore.application.gateway.TokenGateway;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "1 - Autenticação", description = "Endpoints para autenticação")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final TokenGateway tokenGateway;

    private final AuthenticationManager authenticationManager;

    public LoginController(TokenGateway tokenGateway, AuthenticationManager authenticationManager) {
        this.tokenGateway = tokenGateway;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO request) {

        logger.info("Request to /login -> POST");

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var userDetails = (UserDetailsAdapter) authentication.getPrincipal();

        String token = tokenGateway.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }
}