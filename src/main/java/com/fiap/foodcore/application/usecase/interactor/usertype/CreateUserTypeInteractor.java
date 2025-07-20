package com.fiap.foodcore.application.usecase.interactor.usertype;

import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.CreateUserTypeUseCase;
import com.fiap.foodcore.application.usecase.FindUserTypeByNameUseCase;
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
        var existingUserTypes = gateway.findByNameIgnoreCase(input.name());

        if (!existingUserTypes.isEmpty()) {
            throw new DuplicatedDataException("Esse tipo de usuário já está cadastrado");
        }

        var userType = UserTypeMapper.toDomain(input.name());
        var userTypeCreated = gateway.save(userType);
        return UserTypeMapper.fromDomain(userTypeCreated);
    }
}
