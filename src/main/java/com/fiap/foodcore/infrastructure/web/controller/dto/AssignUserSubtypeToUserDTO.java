package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.constraints.NotNull;

public record AssignUserSubtypeToUserDTO(@NotNull Long idUserSubtype) {
}
