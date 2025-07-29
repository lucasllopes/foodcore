package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.AssignUserSubtypeToUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;

public interface AssignUserSubtypeToUserUseCase {
    CreateUserOutput execute(Long id, AssignUserSubtypeToUserInput input);
}
