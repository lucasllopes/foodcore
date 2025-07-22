package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.constraints.NotNull;

public record AssignUserTypeRequestDTO(
        @NotNull(message = "Tipo do usuário é obrigatório")
        Long tipoUsuarioId
) {
}

