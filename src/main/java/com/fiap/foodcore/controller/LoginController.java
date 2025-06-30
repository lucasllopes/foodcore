package com.fiap.foodcore.controller;

import com.fiap.foodcore.dto.LoginRequestDTO;
import com.fiap.foodcore.infrastructure.UserDetailsAdapter;
import com.fiap.foodcore.core.port.out.TokenServicePort;
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

    private final TokenServicePort tokenServicePort;

    private final AuthenticationManager authenticationManager;

    public LoginController(TokenServicePort tokenServicePort, AuthenticationManager authenticationManager) {
        this.tokenServicePort = tokenServicePort;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO request) {

        logger.info("Request to /login -> POST");

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var userDetails = (UserDetailsAdapter) authentication.getPrincipal();

        String token = tokenServicePort.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }
}