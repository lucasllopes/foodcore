package com.fiap.foodcore.infrastructure.gateways;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiap.foodcore.exception.TokenJwtException;
import com.fiap.foodcore.application.gateway.TokenGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.*;

@Service
public class TokenJwtGateway implements TokenGateway {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(String username) {

        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT
                    .create()
                    .withIssuer("Foodcore")
                    .withSubject(username)
                    .withExpiresAt(tokenExpiration(30))
                    .sign(algoritmo);
        }catch (JWTCreationException e){
            throw new TokenJwtException("Erro ao gerar JWT.");
        }

    }

    @Override
    public String validateToken(String token){
        DecodedJWT decodedJWT;
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("Foodcore").build();
            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        }catch (JWTVerificationException e){
            throw new TokenJwtException("Token inválido.");
        }
    }

    public Instant tokenExpiration(Integer minutos){
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        return LocalDateTime.now(zoneId)
                .plusMinutes(minutos)
                .atZone(zoneId)
                .toInstant();
    }
}