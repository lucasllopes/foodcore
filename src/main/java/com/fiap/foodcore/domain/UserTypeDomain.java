package com.fiap.foodcore.domain;

import com.fiap.foodcore.domain.exception.UserSubtypeNotFoundException;

import java.util.Arrays;

public enum UserTypeDomain {
    CLIENTE,
    COLABORADOR,
    DONO;

    public static UserTypeDomain fromString(String value) {
        return Arrays.stream(UserTypeDomain.values())
                .filter(t -> t.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new UserSubtypeNotFoundException("Valor inválido para tipo de usuário"));
    }

    public static boolean isOwner(UserTypeDomain userTypeDomain){
        return userTypeDomain == UserTypeDomain.DONO;
    }
}