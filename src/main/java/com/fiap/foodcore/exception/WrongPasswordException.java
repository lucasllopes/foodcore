package com.fiap.foodcore.exception;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(String message) {
        super(message);
    }
}
