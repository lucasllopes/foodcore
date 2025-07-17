package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;

public interface CreateUserTypeUseCase {

    CreateUserTypeOutput execute(CreateUserTypeInput input);
}
