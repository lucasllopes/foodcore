package com.fiap.g10.g10auth.persistence.entity;

public enum TipoUsuario {
    CLIENTE("Cliente"),
    DONO("Dono de Restaurante");

    private final String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }
}