package com.fiap.foodcore.dto;

import com.fiap.foodcore.adapter.out.persistence.entity.UserType;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        UserType tipo,
        List<AddressResponseDTO> enderecos
) {}