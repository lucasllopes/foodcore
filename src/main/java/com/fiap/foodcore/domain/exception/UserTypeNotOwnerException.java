package com.fiap.foodcore.domain.exception;

public class UserTypeNotOwnerException extends IllegalArgumentException {

    public UserTypeNotOwnerException(String message) {
        super(message);
    }
}
