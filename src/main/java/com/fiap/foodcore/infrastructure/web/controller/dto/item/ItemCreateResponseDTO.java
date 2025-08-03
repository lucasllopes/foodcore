package com.fiap.foodcore.infrastructure.web.controller.dto.item;

import java.math.BigDecimal;

public record ItemCreateResponseDTO(
        Long id,

        String name,

        String description,

        BigDecimal price,

        String availability,

        String photo,

        Long ownerId
) {
}
