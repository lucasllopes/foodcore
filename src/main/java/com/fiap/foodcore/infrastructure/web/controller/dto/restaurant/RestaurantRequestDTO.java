package com.fiap.foodcore.infrastructure.web.controller.dto.restaurant;

import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.List;

public record RestaurantRequestDTO(

        @NotBlank(message = "Nome do restaurante é obrigatório")
        String nome,

        @NotNull(message = "Necessário informar um endereço.")
        @NotEmpty(message = "A lista de endereços não pode estar vazia.")
        @Valid
        List<AddressResponseDTO> address, // List<AddressCreateRequestDTO> ?

        @NotBlank(message = "Tipo de cozinha é obrigatório")
        String cuisineType,

        @NotBlank(message = "Horário de início de funcionamento é obrigatório")
        String openingHours,

        @NotBlank(message = "Horário de fim de funcionamento é obrigatório")
        String closingHours,

        @NotEmpty(message = "É obrigatório referenciar o dono do restaurante")
        Long ownerId) {
}
