package com.fiap.foodcore.application.usecase.interactor.usersubtype;

import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.CreateUserSubtypeUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.mapper.UserSubtypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;

public class CreateUserSubtypeInteractor implements CreateUserSubtypeUseCase {

    private final UserSubtypeGateway gateway;

    public CreateUserSubtypeInteractor(UserSubtypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CreateUserSubtypeOutput execute(CreateUserSubtypeInput input){
        var existingUserTypes = gateway.findByNameIgnoreCase(input.name());

        if (!existingUserTypes.isEmpty()) {
            throw new DuplicatedDataException("Esse tipo de usuário já está cadastrado");
        }

        var userType = UserSubtypeMapper.toDomain(input.name());
        var userTypeCreated = gateway.save(userType);
        return UserSubtypeMapper.fromDomain(userTypeCreated);
    }
}
