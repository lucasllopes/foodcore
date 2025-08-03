package com.fiap.foodcore.infrastructure.web.controller.dto.menu;

import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateResponseDTO;

import java.util.List;

public record MenuResponseDTO(
        Long id,
        String name,
        String description,
        Long restaurantId,
        List<ItemCreateResponseDTO> items
){

}
