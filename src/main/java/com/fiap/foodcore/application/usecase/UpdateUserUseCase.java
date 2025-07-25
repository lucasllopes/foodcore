package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;

public interface UpdateUserUseCase {
    CreateUserOutput execute(Long id, UpdateUserInput input);
}
