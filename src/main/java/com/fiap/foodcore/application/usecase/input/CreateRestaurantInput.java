package com.fiap.foodcore.application.usecase.input;

import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;

import java.time.LocalTime;
import java.util.List;

public record CreateRestaurantInput(String name,
                                    List<CreateAddressInput> address,
                                    String cuisineType,
                                    LocalTime openingHours,
                                    LocalTime closingHours,
                                    Long ownerId) {
}