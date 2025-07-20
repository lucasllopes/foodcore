package com.fiap.foodcore.application.usecase.output;

import java.util.List;

public record MenuCreateOutput(
        Long id,
        String name,
        String description,
        List<ItemOutput> items) {
}
