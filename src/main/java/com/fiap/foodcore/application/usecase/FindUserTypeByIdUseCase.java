package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;

public interface FindUserTypeByIdUseCase {
    CreateUserTypeOutput execute(Long id);
}
