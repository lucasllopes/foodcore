package com.fiap.foodcore.service.strategy;

import com.fiap.foodcore.persistence.entity.UserType;

public interface CreateUserStrategyFactory {
    CreateUserStrategy getStrategy(UserType userType);
}
