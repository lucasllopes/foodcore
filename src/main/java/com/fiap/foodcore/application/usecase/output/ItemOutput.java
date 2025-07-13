package com.fiap.foodcore.application.usecase.output;

import java.math.BigDecimal;

public record ItemOutput(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String availability,
        String photo
) {
}
