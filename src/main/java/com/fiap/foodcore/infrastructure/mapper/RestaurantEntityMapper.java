package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantAddressEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;

import java.util.stream.Collectors;

public class RestaurantEntityMapper {
    public static Restaurant toDomain(RestaurantEntity entity) {
        if (entity == null) return null;

        Restaurant restaurant = Restaurant.create(
                entity.getId(),
                entity.getName(),
                RestaurantAddressEntityMapper.toDomain(entity.getAddress()),
                entity.getCuisineType(),
                entity.getOpeningHours(),
                entity.getClosingHours(),
                entity.getOwner().getId()
        );

        return restaurant;
    }

    public static RestaurantEntity toEntity(Restaurant domain) {
        if (domain == null) return null;

        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setAddress(RestaurantAddressEntityMapper.toEntity(domain.getAddress()));
        entity.setCuisineType(domain.getCuisineType());
        entity.setOpeningHours(domain.getOpeningHours());
        entity.setClosingHours(domain.getClosingHours());
        //TODO: Rever questão do owner
        UserEntity owner = new UserEntity();
        owner.setId(domain.getOwnerId());
        entity.setOwner(owner);
        return entity;
    }
}
