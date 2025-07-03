package com.fiap.foodcore.application.gateway;

public interface TokenGateway {

    String generateToken(String subject);
    String validateToken(String token);
}