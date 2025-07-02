package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.UserDetailsAdapter;

public interface TokenGateway {

    String generateToken(UserDetailsAdapter subject);
    String validateToken(String token);
}