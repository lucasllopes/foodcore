package com.fiap.foodcore.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fiap.foodcore.exception.TokenJwtException;
import com.fiap.foodcore.infrastructure.UserDetailsAdapter;
import com.fiap.foodcore.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    //private final String secret = "b2JkdBz!P9lKb2$q8KqLzJQbP29sWzVg";

    @Override
    public String generateToken(UserDetailsAdapter usuario) {

        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT
                    .create()
                    .withIssuer("Foodcore")
                    .withSubject(usuario.getUsername())
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
        //TODO: configurar horario local do docker
        return LocalDateTime.now().plusMinutes(minutos).toInstant(ZoneOffset.of("+00:00"));
    }
}