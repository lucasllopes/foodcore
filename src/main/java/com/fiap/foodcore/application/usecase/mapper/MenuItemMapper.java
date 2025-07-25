package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.infrastructure.web.controller.dto.ItemUpdateRequestDTO;


public class MenuItemMapper {
    public static Item fromDto(ItemUpdateRequestDTO menuItemUpdateRequestDTO) {
        return new Item.Builder()
                .id(menuItemUpdateRequestDTO.id())
                .name(menuItemUpdateRequestDTO.name())
                .description(menuItemUpdateRequestDTO.description())
                .price(menuItemUpdateRequestDTO.price())
                .availability(menuItemUpdateRequestDTO.availability())
                .photo(menuItemUpdateRequestDTO.photo())
                .build();
    }

    public static ItemCreateOutput toOutput(Item item) {
        return new ItemCreateOutput(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailability(),
                item.getPhoto()
        );
    }

}
