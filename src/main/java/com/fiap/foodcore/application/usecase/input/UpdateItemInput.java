package com.fiap.foodcore.application.usecase.input;

import java.math.BigDecimal;

public record UpdateItemInput(
        String name,
        String description,
        BigDecimal price,
        String availability,
        String photo
) {
}
