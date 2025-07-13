package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;

import java.util.stream.Collectors;

public class RestaurantEntityMapper {
    public static Restaurant toDomain(RestaurantEntity entity) {
        if (entity == null) return null;

        Restaurant restaurant = Restaurant.create(
                entity.getId(),
                entity.getName(),
                RestaurantAddressEntityMapper.toDomain(entity.getAddresses()),
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
        entity.setName(domain.getName());
        if (domain.getAddress() != null && !domain.getAddress().isEmpty()) {
            var addressEntities = domain.getAddress()
                    .stream()
                    .map(RestaurantAddressEntityMapper::toEntity)
                    .collect(Collectors.toList());

            entity.setAddresses(addressEntities);
        }
        entity.setCuisineType(domain.getCuisineType());
        entity.setOpeningHours(domain.getOpeningHours());
        entity.setClosingHours(domain.getClosingHours());
        UserEntity owner = new UserEntity();
        owner.setId(domain.getOwnerId());
        entity.setOwner(owner);
        return entity;
    }
}
