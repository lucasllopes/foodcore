package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.application.usecase.input.UpdateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.ItemEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;

public class ItemMapper {

    public static ItemEntity toEntity(Item item) {
        if (item == null) return null;
        ItemEntity entity = new ItemEntity();
        entity.setId(item.getId());
        entity.setName(item.getName());
        entity.setDescription(item.getDescription());
        entity.setPrice(item.getPrice());
        entity.setAvailability(item.getAvailability());
        entity.setPhoto(item.getPhoto());

        var owner = UserEntityMapper.toEntity(item.getOwnerId());

        entity.setOwner(owner);

        return entity;
    }

    public static Item toDomain(ItemEntity entity) {
        if (entity == null) return null;
        return new Item.Builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .availability(entity.getAvailability())
                .photo(entity.getPhoto())
                .ownerId(UserEntityMapper.toDomain(entity.getOwner()))
                .build();
    }

    public static ItemCreateOutput fromDomain(Item item){
        if (item == null) return null;
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

    public static Item toDomain(UpdateItemInput input) {
        if (input == null) return null;
        return new Item.Builder()
                .name(input.name())
                .description(input.description())
                .price(input.price())
                .availability(input.availability())
                .photo(input.photo())
                .build();
    }

}