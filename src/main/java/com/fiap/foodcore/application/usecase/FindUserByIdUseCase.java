package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.output.CreateUserOutput;

public interface FindUserByIdUseCase {
    CreateUserOutput execute(Long id);
}
