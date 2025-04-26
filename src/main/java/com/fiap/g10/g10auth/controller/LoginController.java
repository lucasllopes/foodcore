package com.fiap.g10.g10auth.controller;

import com.fiap.g10.g10auth.dto.LoginRequest;
import com.fiap.g10.g10auth.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService login;

    public LoginController(LoginService login) {
        this.login = login;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        boolean autenticado = login.autenticar(request.login(), request.senha());

        return autenticado
                ? ResponseEntity.ok("Login realizado com sucesso")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidas");
    }
}
