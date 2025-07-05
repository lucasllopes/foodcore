package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.UserTypeDomain;

public class CreateUserInteractor {

    private final CreateUserStrategyFactory strategyFactory;

    public CreateUserInteractor(CreateUserStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public CreateUserOutput execute(CreateUserInput createUserInput) {
        var userType = UserTypeDomain.fromString(createUserInput.tipo().name());
        var strategy = strategyFactory.getStrategy(userType);
        return strategy.create(createUserInput);
    }
}
