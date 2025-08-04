package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemUpdateRequestDTO;


public class MenuItemMapper {
    public static Item fromDto(ItemUpdateRequestDTO itemUpdateRequestDTO) {
        return new Item.Builder()
                .id(itemUpdateRequestDTO.id())
                .name(itemUpdateRequestDTO.name())
                .description(itemUpdateRequestDTO.description())
                .price(itemUpdateRequestDTO.price())
                .availability(itemUpdateRequestDTO.availability())
                .photo(itemUpdateRequestDTO.photo())
                .build();
    }

    public static ItemCreateOutput toOutput(Item item) {
        return new ItemCreateOutput(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailability(),
                item.getPhoto(),
                item.getOwnerId().getId()
        );
    }

}
