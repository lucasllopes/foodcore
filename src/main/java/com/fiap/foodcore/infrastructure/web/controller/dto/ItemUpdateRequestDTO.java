package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.math.BigDecimal;

public record ItemUpdateRequestDTO(

        Long id,

        String name,

        String description,

        BigDecimal price,

        String availability,

        String photo
) {
}
