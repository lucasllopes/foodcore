package com.fiap.g10.g10auth.persistence.entity;

import org.springframework.security.core.GrantedAuthority;

public enum TipoUsuario implements GrantedAuthority {
    CLIENTE("Cliente"),
    DONO("Dono de Restaurante");

    private final String descricao;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }
}