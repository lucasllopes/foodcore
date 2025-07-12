package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;

import java.util.stream.Collectors;

public class RestaurantEntityMapper {
    public static Restaurant toDomain(RestaurantEntity entity) {
        return null;
    }

    public static RestaurantEntity toEntity(Restaurant domain) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        if (domain.getAddress() != null && !domain.getAddress().isEmpty()) {
            var addressEntities = domain.getAddress()
                    .stream()
                    .map(AddressEntityMapper::toEntity)
                    .collect(Collectors.toList());

            entity.setAddrres(addressEntities);
        }
        entity.setCuisineType(domain.getCuisineType());
        entity.setOpeningHours(domain.getOpeningHours());
        entity.setOwnerId(domain.getOwnerId());
        return entity;
    }
}
