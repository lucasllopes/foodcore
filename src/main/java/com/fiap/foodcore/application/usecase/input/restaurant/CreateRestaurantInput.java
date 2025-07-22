package com.fiap.foodcore.application.usecase.input.restaurant;

import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;

import java.time.LocalTime;
import java.util.List;

public record CreateRestaurantInput(String name,
                                    CreateAddressInput address,
                                    String cuisineType,
                                    LocalTime openingHours,
                                    LocalTime closingHours,
                                    Long ownerId) {
}