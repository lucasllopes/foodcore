package com.fiap.foodcore.application.usecase.output;

import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public record CreateRestaurantOutput(Long id,
                                     String nome,
                                     List<AddressOutput> address,
                                     String cuisineType,
                                     LocalTime openingHours,
                                     LocalTime closingHours,
                                     Long ownerId) {
}
