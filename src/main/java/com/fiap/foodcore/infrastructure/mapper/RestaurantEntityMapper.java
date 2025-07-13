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
                RestaurantAddressEntityMapper.toDomain(entity.getAddress()),
                entity.getCuisineType(),
                entity.getOpeningHours(),
                entity.getClosingHours(),
                entity.getOwnerId()
        );

        return restaurant;
    }

    public static RestaurantEntity toEntity(Restaurant domain) {
        if (domain == null) return null;

        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        if (domain.getAddress() != null && !domain.getAddress().isEmpty()) {
            var addressEntities = domain.getAddress()
                    .stream()
                    .map(RestaurantAddressEntityMapper::toEntity)
                    .collect(Collectors.toList());

            entity.setAddress(addressEntities);
        }
        entity.setCuisineType(domain.getCuisineType());
        entity.setOpeningHours(domain.getOpeningHours());
        entity.setOwnerId(domain.getOwnerId());
        return entity;
    }
}
