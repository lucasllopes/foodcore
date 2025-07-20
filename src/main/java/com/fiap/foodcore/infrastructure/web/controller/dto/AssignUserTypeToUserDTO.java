package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.constraints.NotNull;

public record AssignUserTypeToUserDTO(@NotNull Long idUserType) {
}
