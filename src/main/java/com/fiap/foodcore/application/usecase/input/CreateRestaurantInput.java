package com.fiap.foodcore.application.usecase.input;

import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;

import java.util.List;

public record CreateRestaurantInput(String name,
                                    List<CreateAddressInput> address,
                                    String cuisineType,
                                    String openingHours,
                                    String closingHours,
                                    Long ownerId) {
}