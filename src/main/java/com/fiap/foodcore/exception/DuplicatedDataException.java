package com.fiap.foodcore.exception;

public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException(String mensagem) {
        super(mensagem);
    }
}