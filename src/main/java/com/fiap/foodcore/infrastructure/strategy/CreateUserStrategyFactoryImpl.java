package com.fiap.foodcore.infrastructure.strategy;


import com.fiap.foodcore.application.strategy.CreateUserStrategy;
import com.fiap.foodcore.application.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.domain.UserTypeDomain;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateUserStrategyFactoryImpl implements CreateUserStrategyFactory {

    private final Map<UserTypeDomain, CreateUserStrategy> strategies;

    public CreateUserStrategyFactoryImpl(
            @Qualifier("customer") CreateUserStrategy customer,
            @Qualifier("owner") CreateUserStrategy owner
    ) {
        this.strategies = Map.of(
                UserTypeDomain.CLIENTE, customer,
                UserTypeDomain.DONO, owner
        );
    }

    @Override
    public CreateUserStrategy getStrategy(UserTypeDomain userType) {
        return strategies.get(userType);
    }
}
