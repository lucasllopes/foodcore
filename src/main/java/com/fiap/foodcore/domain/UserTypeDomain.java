package com.fiap.foodcore.domain;

import com.fiap.foodcore.exception.UserTypeNotFoundException;

import java.util.Arrays;

public enum UserTypeDomain {
    CLIENTE,
    DONO;

    public static UserTypeDomain fromString(String value) {
        return Arrays.stream(UserTypeDomain.values())
                .filter(t -> t.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new UserTypeNotFoundException("Valor inválido para tipo de usuário"));
    }
}