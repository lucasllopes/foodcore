package com.fiap.foodcore.application.usecase.output;

import java.math.BigDecimal;

public record ItemCreateOutput(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String availability,
        String photo,
        Long ownerId
) {
}
