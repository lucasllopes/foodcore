package com.fiap.foodcore.infrastructure.web.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MenuCreateRequestDTO (
        @NotBlank(message = "Nome é obrigatório.")
        String name,
        @NotBlank(message = "Descrição é obrigatória.")
        String description,

        @NotNull(message = "O restaurantId é obrigatório")
        Long restaurantId,

        @NotNull(message = "Necessário informar um endereço.")
        @NotEmpty(message = "A lista de endereços não pode estar vazia.")
        @Valid
        List<ItemCreateRequestDTO> items) {}
