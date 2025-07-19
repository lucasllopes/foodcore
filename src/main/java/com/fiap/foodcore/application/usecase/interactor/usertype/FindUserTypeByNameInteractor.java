package com.fiap.foodcore.application.usecase.interactor.usertype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.FindUserTypeByNameUseCase;
import com.fiap.foodcore.application.usecase.mapper.UserTypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;

public class FindUserTypeByNameInteractor implements FindUserTypeByNameUseCase {

    private final UserTypeGateway gateway;

    public FindUserTypeByNameInteractor(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CreateUserTypeOutput execute(String name) {
        return gateway.findByNameIgnoreCase(name)
                .map(UserTypeMapper::fromDomain)
                .orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));
    }
}

