package com.fiap.foodcore.infrastructure.web.controller.dto;

import com.fiap.foodcore.infrastructure.web.controller.dto.AddressCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RestaurantCreateRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Tipo de cozinha é obrigatório")
        String cuisineType,

        @NotNull(message = "Necessário informar um endereço.")
        @NotEmpty(message = "A lista de endereços não pode estar vazia.")
        @Valid
        List<AddressCreateRequestDTO> enderecos,
        String openingHours, String closingHours, Long ownerId
) {}