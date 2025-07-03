package com.fiap.foodcore.infrastructure.web.controller.dto;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserType;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        UserType tipo,
        List<AddressResponseDTO> enderecos
) {}