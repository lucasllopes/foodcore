package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.util.List;

public record MenuCreateResponseDTO(
        Long id,
        String name,
        String description,
        Long restaurantId,
        List<ItemCreateResponseDTO> items
){

}
