package com.fiap.foodcore.application.usecase.interactor.usertype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.FindUserTypeByNameUseCase;
import com.fiap.foodcore.application.usecase.mapper.UserTypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.domain.UserType;

import java.util.List;

public class FindUserTypeByNameInteractor implements FindUserTypeByNameUseCase {

    private final UserTypeGateway gateway;

    public FindUserTypeByNameInteractor(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<CreateUserTypeOutput> execute(String name) {
        return gateway.findByNameIgnoreCase(name).stream()
                .map(UserTypeMapper::fromDomain)
                .toList();
    }
}