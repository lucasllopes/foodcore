package com.fiap.foodcore.application.usecase.output;

import com.fiap.foodcore.domain.Item;

import java.util.List;

public record MenuOutput(
       Long id,
        String name,
        String description,
        List<ItemOutput>items) {
}
