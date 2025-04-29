package com.fiap.g10.g10auth.controller;

import com.fiap.g10.g10auth.dto.LoginRequest;
import com.fiap.g10.g10auth.service.LoginService;
import com.fiap.g10.g10auth.service.impl.LoginServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {

        logger.info("Request para /login -> POST");

        boolean autenticado = loginService.autenticar(request.login(), request.senha());

        return autenticado
                ? ResponseEntity.ok("Login realizado com sucesso")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidas");
    }
}
