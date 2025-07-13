package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.domain.UserType;

public class UserTypeMapper {

    public static UserType toDomain(CreateUserTypeInput input) {
        return UserType.create(input.name());
    }

    public static CreateUserTypeOutput fromDomain(UserType userType) {
        return new CreateUserTypeOutput(userType.getId(), userType.getName()
        );
    }

}
