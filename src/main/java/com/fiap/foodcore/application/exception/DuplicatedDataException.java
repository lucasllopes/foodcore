package com.fiap.foodcore.application.exception;

public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException(String message) {
        super(message);
    }
}