package com.fiap.foodcore.exception;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String mensagem) {
        super(mensagem);
    }
}
