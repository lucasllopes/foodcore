package com.fiap.foodcore.infrastructure.web.controller.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressUpdateRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

public record RestaurantUpdateRequestDTO(@NotBlank(message = "Nome é obrigatório")
                                   String name,

                                         @NotNull(message = "Tipo de cozinha é obrigatório")
                                   String cuisineType,
                                         @DateTimeFormat(pattern = "HH:mm")
                                   @JsonFormat(pattern = "HH:mm")
                                   LocalTime openingHours,
                                         @DateTimeFormat(pattern = "HH:mm")
                                   @JsonFormat(pattern = "HH:mm")
                                   LocalTime closingHours,
                                         @NotNull(message = "Necessário informar um endereço.")
                                   AddressUpdateRequestDTO endereco
) {
}