package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.AddressUpdateInput;

import java.time.LocalTime;
import java.util.List;

public record UpdateRestaurantInput(
        String nome,

        AddressUpdateInput enderecos,
        String cuisineType,
        LocalTime openingHours,
        LocalTime closingHours
) {}