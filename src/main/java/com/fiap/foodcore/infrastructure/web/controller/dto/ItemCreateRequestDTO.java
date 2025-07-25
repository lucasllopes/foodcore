package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemCreateRequestDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String name,
        @NotBlank(message = "Descrição é obrigatório.")
        String description,

        @NotNull(message = "Preço é obrigatório.")
        @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero.")
        BigDecimal price,

        @NotBlank(message = "Disponibilidade é obrigatório.")
        String availability,

        @NotBlank(message = "Caminho da foto é obrigatório.")
        String photo
) {
}
