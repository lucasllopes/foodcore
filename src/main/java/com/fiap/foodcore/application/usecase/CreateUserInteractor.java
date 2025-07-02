package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserType;
import com.fiap.foodcore.application.service.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;

public class CreateUserInteractor {

    private final CreateUserStrategyFactory strategyFactory;

    public CreateUserInteractor(CreateUserStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public UserResponseDTO execute(UserCreateRequestDTO dto) {
        var userType = UserType.fromString(dto.tipo());
        var strategy = strategyFactory.getStrategy(userType);
        return strategy.create(dto);
    }
}
