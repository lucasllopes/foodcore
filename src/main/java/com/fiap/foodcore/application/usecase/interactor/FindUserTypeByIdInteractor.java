package com.fiap.foodcore.application.usecase.interactor;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.FindUserTypeByIdUseCase;
import com.fiap.foodcore.application.usecase.mapper.UserTypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;

public class FindUserTypeByIdInteractor implements FindUserTypeByIdUseCase {

    private final UserTypeGateway gateway;

    public FindUserTypeByIdInteractor(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CreateUserTypeOutput execute(Long id) {
        return gateway.findById(id)
                .map(UserTypeMapper::fromDomain)
                .orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));
    }
}
