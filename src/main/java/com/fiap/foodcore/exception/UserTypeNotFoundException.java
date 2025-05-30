package com.fiap.foodcore.exception;

public class UserTypeNotFoundException extends IllegalArgumentException {

    public UserTypeNotFoundException(String message) {
        super(message);
    }
}
