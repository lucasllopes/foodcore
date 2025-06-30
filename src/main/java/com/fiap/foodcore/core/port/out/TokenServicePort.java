package com.fiap.foodcore.core.port.out;

public interface TokenServicePort {

    String generateToken(String subject);
    String validateToken(String token);
}