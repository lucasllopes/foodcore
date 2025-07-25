package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.math.BigDecimal;

public record ItemUpdateResponseDTO(
        Long id,

        String name,

        String description,

        BigDecimal price,

        String availability,

        String photo
) {
}
