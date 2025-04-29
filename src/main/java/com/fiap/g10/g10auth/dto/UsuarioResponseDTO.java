package com.fiap.g10.g10auth.dto;

import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipo,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cep,
        String estado,
        String cidade
) {}