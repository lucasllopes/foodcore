package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;

public interface CreateUserUseCase {
    CreateUserOutput execute(CreateUserInput createUserInput);
}
