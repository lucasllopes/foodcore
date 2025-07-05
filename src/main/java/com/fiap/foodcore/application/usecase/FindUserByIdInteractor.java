package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.exception.DataNotFoundException;

public class FindUserByIdInteractor {

    private final UserGateway userGateway;

    public FindUserByIdInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public CreateUserOutput execute(Long id) {
        return userGateway.findById(id)
                .map(UserMapper::fromDomain)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
    }
}