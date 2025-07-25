package com.fiap.foodcore.domain.exception;

public class UserSubtypeNotFoundException extends IllegalArgumentException {

    public UserSubtypeNotFoundException(String message) {
        super(message);
    }
}
