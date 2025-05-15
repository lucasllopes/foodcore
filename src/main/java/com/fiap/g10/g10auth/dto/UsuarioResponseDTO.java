package com.fiap.g10.g10auth.dto;

import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;

import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipo,
        List<EnderecoResponseDTO> enderecos
) {}