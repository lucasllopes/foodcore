package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.output.ItemOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuItemUpdateRequestDTO;


public class MenuItemMapper {
    public static Item fromDto(MenuItemUpdateRequestDTO menuItemUpdateRequestDTO) {
        return new Item.Builder()
                .name(menuItemUpdateRequestDTO.name())
                .description(menuItemUpdateRequestDTO.description())
                .price(menuItemUpdateRequestDTO.price())
                .photo(menuItemUpdateRequestDTO.photo())
                .build();
    }

    public static ItemOutput toOutput(Item item) {
        return new ItemOutput(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailability(),
                item.getPhoto()
        );
    }

}
