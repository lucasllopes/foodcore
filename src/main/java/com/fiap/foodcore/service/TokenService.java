package com.fiap.foodcore.service;

import com.fiap.foodcore.infrastructure.UserDetailsAdapter;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String generateToken(UserDetailsAdapter usuario);

    String validateToken(String token);
}