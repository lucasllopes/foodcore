package com.fiap.foodcore.application.exception;

public class TokenJwtException extends RuntimeException{

    public TokenJwtException(String message) {
        super(message);
    }
}
