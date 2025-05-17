package com.fiap.foodcore.service.strategy;


import com.fiap.foodcore.persistence.entity.UserType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateUserStrategyFactoryImpl implements CreateUserStrategyFactory {

    private final Map<UserType, CreateUserStrategy> strategies;

    public CreateUserStrategyFactoryImpl(
            @Qualifier("customer") CreateUserStrategy customer,
            @Qualifier("owner") CreateUserStrategy owner
    ) {
        this.strategies = Map.of(
                UserType.CLIENTE, customer,
                UserType.DONO, owner
        );
    }

    @Override
    public CreateUserStrategy getStrategy(UserType userType) {
        return strategies.get(userType);
    }
}
