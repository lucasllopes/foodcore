package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.domain.UserType;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserTypeEntity;

public class UserTypeEntityMapper {

    public static UserType toDomain(UserTypeEntity entity) {
        if (entity == null) return null;

        UserType userType = UserType.reconstruct(entity.getId(),entity.getName());

        return userType;
    }


    public static UserTypeEntity toEntity(UserType domain) {
        if (domain == null) return null;

        UserTypeEntity entity = new UserTypeEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());

        return entity;
    }
}
