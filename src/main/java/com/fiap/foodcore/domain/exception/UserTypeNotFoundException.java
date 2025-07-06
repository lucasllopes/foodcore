package com.fiap.foodcore.domain.exception;

public class UserTypeNotFoundException extends IllegalArgumentException {

    public UserTypeNotFoundException(String message) {
        super(message);
    }
}
