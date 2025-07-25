package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.util.List;

public record MenuUpdateRequestDTO(
        String name,
        String description,
        Long restaurantId,
        List<ItemUpdateRequestDTO> items
) {
}
