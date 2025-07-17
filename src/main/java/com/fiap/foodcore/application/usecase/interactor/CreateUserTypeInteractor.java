package com.fiap.foodcore.application.usecase.interactor;

import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.CreateUserTypeUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.mapper.UserTypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;

public class CreateUserTypeInteractor implements CreateUserTypeUseCase {

    private final UserTypeGateway gateway;

    public CreateUserTypeInteractor(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CreateUserTypeOutput execute(CreateUserTypeInput input){
        var userType = UserTypeMapper.toDomain(input);
        var userTypeCreated = gateway.save(userType);
        return UserTypeMapper.fromDomain(userTypeCreated);

    }
}
