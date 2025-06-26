package com.fiap.foodcore.application.port.out;

public interface TokenService {

    String generateToken(String subject);

    String validateToken(String token);
}