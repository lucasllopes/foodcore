package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.adapter.out.persistence.entity.UserType;
import com.fiap.foodcore.application.service.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.core.port.in.CreateUsersUseCase;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CreateUserInteractor implements CreateUsersUseCase {

    private final CreateUserStrategyFactory strategyFactory;

    public CreateUserInteractor(CreateUserStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public UserResponseDTO execute(UserCreateRequestDTO dto) {
        var userType = UserType.fromString(dto.tipo());
        var strategy = strategyFactory.getStrategy(userType);
        return strategy.create(dto);
    }
}
