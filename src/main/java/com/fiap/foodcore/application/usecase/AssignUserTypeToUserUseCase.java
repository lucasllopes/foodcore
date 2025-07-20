package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.AssignUserTypeToUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;

public interface AssignUserTypeToUserUseCase {
    CreateUserOutput execute(Long id, AssignUserTypeToUserInput input);
}
