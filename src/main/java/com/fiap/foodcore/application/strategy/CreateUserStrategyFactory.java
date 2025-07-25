package com.fiap.foodcore.application.strategy;


import com.fiap.foodcore.domain.UserTypeDomain;

public interface CreateUserStrategyFactory {
    CreateUserStrategy getStrategy(UserTypeDomain userType);
}
