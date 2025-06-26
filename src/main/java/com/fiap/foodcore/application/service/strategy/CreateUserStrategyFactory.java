package com.fiap.foodcore.application.service.strategy;

import com.fiap.foodcore.adapter.out.persistence.entity.UserType;

public interface CreateUserStrategyFactory {
    CreateUserStrategy getStrategy(UserType userType);
}
