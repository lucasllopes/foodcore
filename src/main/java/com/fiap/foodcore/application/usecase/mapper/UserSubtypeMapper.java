package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserSubtypeOutput;
import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserSubtypeEntity;


public class UserSubtypeMapper {

    public static UserSubtype toDomain(String name) {
        return UserSubtype.create(name);
    }

    public static CreateUserSubtypeOutput fromDomain(UserSubtype userType) {
        return new CreateUserSubtypeOutput(userType.getId(), userType.getName()
        );
    }

    public static UpdateUserSubtypeOutput fromUpdateDomain(UserSubtype userType) {
        return new UpdateUserSubtypeOutput(userType.getId(), userType.getName(), userType.getLastModified()
        );
    }

    public static UserSubtypeEntity toEntity(UserSubtype userType) {
        if (userType == null) return null;

        UserSubtypeEntity entity = new UserSubtypeEntity();
        entity.setId(userType.getId());
        entity.setName(userType.getName());
        entity.setLastModified(userType.getLastModified());
        return entity;
    }
}
