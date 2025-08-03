package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MenuUpdateRequestDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String name,

        @NotBlank(message = "Descrição é obrigatória.")
        String description,

        @NotNull(message = "O código do restaurante (restaurantId) é obrigatório")
        Long restaurantId,

        @NotNull(message = "Necessário informar items.")
        @NotEmpty(message = "A lista de items não pode estar vazia.")
        @Valid
        List<ItemUpdateRequestDTO> items
) {
}
