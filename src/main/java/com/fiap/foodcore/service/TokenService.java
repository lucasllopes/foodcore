package com.fiap.foodcore.service;

import com.fiap.foodcore.infrastructure.UserDetailsAdapter;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String geraToken(UserDetailsAdapter usuario);

    String verificaToken(String token);
}