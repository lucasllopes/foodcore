package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.math.BigDecimal;

public record MenuItemUpdateRequestDTO(
        String name,

        String description,

        BigDecimal price,

        String availability,

        String photo
) {
}
