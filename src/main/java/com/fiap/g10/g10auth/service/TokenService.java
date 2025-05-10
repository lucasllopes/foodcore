package com.fiap.g10.g10auth.service;

import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.infrastructure.UsuarioDetailsAdapter;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String geraToken(UsuarioDetailsAdapter usuario);

    String verificaToken(String token);
}