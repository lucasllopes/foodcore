package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserSubtypeRequestDTO(@NotEmpty String name) {
}
