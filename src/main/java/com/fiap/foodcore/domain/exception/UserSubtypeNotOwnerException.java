package com.fiap.foodcore.domain.exception;

public class UserSubtypeNotOwnerException extends IllegalArgumentException {

    public UserSubtypeNotOwnerException(String message) {
        super(message);
    }
}
