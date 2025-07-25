package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserSubtypeEntity;

public class UserSubtypeEntityMapper {

    public static UserSubtype toDomain(UserSubtypeEntity entity) {
        if (entity == null) return null;

        UserSubtype userType = UserSubtype.reconstruct(entity.getId(),entity.getName(), entity.getLastModified());

        return userType;
    }


    public static UserSubtypeEntity toEntity(UserSubtype domain) {
        if (domain == null) return null;

        UserSubtypeEntity entity = new UserSubtypeEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setLastModified(domain.getLastModified());

        return entity;
    }
}
