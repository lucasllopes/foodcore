package com.fiap.foodcore.infrastructure.web.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.foodcore.domain.UserTypeDomain;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        UserTypeDomain tipo,
        List<AddressResponseDTO> enderecos,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        UserSubtypeResponseDTO tipoUsuario
) {}