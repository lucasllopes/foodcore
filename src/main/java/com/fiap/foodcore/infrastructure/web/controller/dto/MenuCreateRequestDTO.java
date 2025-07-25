package com.fiap.foodcore.infrastructure.web.controller.dto;

import java.util.List;

public record MenuCreateRequestDTO (
        String name,
        String description,
        Long restaurantId,
        List<ItemCreateRequestDTO> items) {}
