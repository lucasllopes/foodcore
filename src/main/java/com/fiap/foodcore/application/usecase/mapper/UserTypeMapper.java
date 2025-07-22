package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserTypeOutput;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.UserType;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.AddressEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserTypeEntity;


public class UserTypeMapper {

    public static UserType toDomain(String name) {
        return UserType.create(name);
    }

    public static CreateUserTypeOutput fromDomain(UserType userType) {
        return new CreateUserTypeOutput(userType.getId(), userType.getName()
        );
    }

    public static UpdateUserTypeOutput fromUpdateDomain(UserType userType) {
        return new UpdateUserTypeOutput(userType.getId(), userType.getName(), userType.getLastModified()
        );
    }

    public static UserTypeEntity toEntity(UserType userType) {
        if (userType == null) return null;

        UserTypeEntity entity = new UserTypeEntity();
        entity.setId(userType.getId());
        entity.setName(userType.getName());
        entity.setLastModified(userType.getLastModified());
        return entity;
    }
}
