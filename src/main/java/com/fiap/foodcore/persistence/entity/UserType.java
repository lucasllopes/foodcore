package com.fiap.foodcore.persistence.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority {
    CLIENTE("Cliente"),
    DONO("Dono de Restaurante");


    private final String description;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

    UserType(String description) {
        this.description = description;
    }
}