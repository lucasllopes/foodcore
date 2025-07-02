package com.fiap.foodcore.application.service.strategy;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserType;

public interface CreateUserStrategyFactory {
    CreateUserStrategy getStrategy(UserType userType);
}
