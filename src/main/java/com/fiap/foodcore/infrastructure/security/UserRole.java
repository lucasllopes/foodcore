package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.domain.UserTypeDomain;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_CLIENTE,
    ROLE_DONO;

    @Override
    public String getAuthority() {
        return name();
    }

    public static UserRole from(UserTypeDomain userTypeDomain) {
        return switch (userTypeDomain) {
            case CLIENTE -> ROLE_CLIENTE;
            case DONO -> ROLE_DONO;
        };
    }
}