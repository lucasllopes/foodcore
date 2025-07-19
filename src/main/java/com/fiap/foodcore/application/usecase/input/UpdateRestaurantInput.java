package com.fiap.foodcore.application.usecase.input;

import java.time.LocalTime;
import java.util.List;

public record UpdateRestaurantInput(
        String nome,

        AddressUpdateInput enderecos,
        String cuisineType,
        LocalTime openingHours,
        LocalTime closingHours
) {}