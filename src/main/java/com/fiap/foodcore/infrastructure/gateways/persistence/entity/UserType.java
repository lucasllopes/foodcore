package com.fiap.foodcore.infrastructure.gateways.persistence.entity;

import com.fiap.foodcore.domain.exception.UserSubtypeNotFoundException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum UserType implements GrantedAuthority {
    CLIENTE("Cliente"),
    COLABORADOR("Colaborador"),
    DONO("Dono de Restaurante");


    private final String description;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

    UserType(String description) {
        this.description = description;
    }

    public static UserType fromString(String text) {

        for (UserType userType : UserType.values()) {
            if (userType.name().equalsIgnoreCase(text)) {
                return userType;
            }
        }

        throw new UserSubtypeNotFoundException("Valores válidos: " + Arrays.toString(UserType.values()));
    }
}