package com.fiap.foodcore.infrastructure.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

public record RestaurantCreateRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Tipo de cozinha é obrigatório")
        String cuisineType,

        @NotNull(message = "Necessário informar um endereço.")
        AddressCreateRequestDTO endereco,
        @DateTimeFormat(pattern = "HH:mm")
        @JsonFormat(pattern = "HH:mm")
        LocalTime openingHours,
        @DateTimeFormat(pattern = "HH:mm")
        @JsonFormat(pattern = "HH:mm")
        LocalTime closingHours,
        @NotNull(message = "Necessário informar o dono.") Long ownerId
) {}