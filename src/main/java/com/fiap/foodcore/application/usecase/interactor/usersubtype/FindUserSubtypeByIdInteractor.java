package com.fiap.foodcore.application.usecase.interactor.usersubtype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.FindUserSubtypeByIdUseCase;
import com.fiap.foodcore.application.usecase.mapper.UserSubtypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;

public class FindUserSubtypeByIdInteractor implements FindUserSubtypeByIdUseCase {

    private final UserSubtypeGateway gateway;

    public FindUserSubtypeByIdInteractor(UserSubtypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CreateUserSubtypeOutput execute(Long id) {
        return gateway.findById(id)
                .map(UserSubtypeMapper::fromDomain)
                .orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));
    }
}
