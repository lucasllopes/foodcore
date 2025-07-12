package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.sql.Time;
import java.util.List;

public record RestaurantResponseDTO(Long id,
                                    String nome,
                                    List<AddressResponseDTO> address,
                                    String cuisineType,
                                    String openingHours,
                                    String closingHours,
                                    Long ownerId) {
}
