package com.fiap.foodcore.domain.exception;

public class RestaurantNotFoundException extends IllegalArgumentException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
