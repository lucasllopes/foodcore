package com.fiap.foodcore.application.usecase.interactor.usersubtype;

import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.FindUserSubtypeByNameUseCase;
import com.fiap.foodcore.application.usecase.mapper.UserSubtypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;

import java.util.List;

public class FindUserSubtypeByNameInteractor implements FindUserSubtypeByNameUseCase {

    private final UserSubtypeGateway gateway;

    public FindUserSubtypeByNameInteractor(UserSubtypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<CreateUserSubtypeOutput> execute(String name) {
        return gateway.findByNameIgnoreCase(name).stream()
                .map(UserSubtypeMapper::fromDomain)
                .toList();
    }
}