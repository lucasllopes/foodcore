package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserTypeRequestDTO(@NotEmpty String name) {
}
