package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public record RestaurantResponseDTO(Long id,
                                    String nome,
                                    List<AddressResponseDTO> address,
                                    String cuisineType,
                                    LocalTime openingHours,
                                    LocalTime closingHours,
                                    Long ownerId) {
}
