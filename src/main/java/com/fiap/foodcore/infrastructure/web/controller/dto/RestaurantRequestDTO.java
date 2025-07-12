package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.sql.Time;
import java.util.List;

public record RestaurantRequestDTO(String nome, List<AddressResponseDTO> address, String cuisineType, String openingHours, String closingHours, UserResponseDTO owner) {
}
