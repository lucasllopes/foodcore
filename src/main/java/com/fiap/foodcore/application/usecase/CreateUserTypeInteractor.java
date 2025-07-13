package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.mapper.UserTypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;

public class CreateUserTypeInteractor {

    private final UserTypeGateway gateway;

    public CreateUserTypeInteractor(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    public CreateUserTypeOutput execute(CreateUserTypeInput input){
        var userType = UserTypeMapper.toDomain(input);
        var userTypeCreated = gateway.save(userType);
        return UserTypeMapper.fromDomain(userTypeCreated);

    }
}
