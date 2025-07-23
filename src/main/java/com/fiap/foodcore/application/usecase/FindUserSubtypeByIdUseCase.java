package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;

public interface FindUserSubtypeByIdUseCase {
    CreateUserSubtypeOutput execute(Long id);
}
