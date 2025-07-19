package com.fiap.foodcore.infrastructure.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public record RestaurantResponseDTO(Long id,
                                    String nome,
                                    AddressResponseDTO address,
                                    String cuisineType,
                                    @DateTimeFormat(pattern = "HH:mm")
                                    @JsonFormat(pattern = "HH:mm")
                                    LocalTime openingHours,
                                    @DateTimeFormat(pattern = "HH:mm")
                                    @JsonFormat(pattern = "HH:mm")
                                    LocalTime closingHours,
                                    Long ownerId) {
}
