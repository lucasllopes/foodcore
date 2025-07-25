package com.fiap.foodcore.infrastructure.mapper;


import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.MenuEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MenuMapper {

    public static MenuEntity toEntity(Menu menu) {
        if (menu == null) return null;
        MenuEntity entity = new MenuEntity();
        entity.setId(menu.getId());
        entity.setName(menu.getName());
        entity.setDescription(menu.getDescription());
        entity.setItems(
                menu.getItems() != null
                        ? menu.getItems().stream()
                        .map(ItemMapper::toEntity)
                        .collect(Collectors.toList())
                        : null
        );
        return entity;
    }

    public static Menu toDomain(MenuEntity entity) {
        if (entity == null) return null;
        Menu.Builder builder = new Menu.Builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription());

        if (entity.getItems() != null) {
            List<Item> items = entity.getItems().stream()
                    .map(ItemMapper::toDomain)
                    .collect(Collectors.toList());
            builder.itemsList(items);
        }

        return builder.build();
    }

    public static MenuCreateOutput fromDomain(Menu menu) {
        if (menu == null) return null;
        return new MenuCreateOutput(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getRestaurantId(),
                menu.getItems() != null
                        ? menu.getItems().stream()
                        .map(ItemMapper::fromDomain)
                        .collect(Collectors.toList())
                        : null
        );
    }

}