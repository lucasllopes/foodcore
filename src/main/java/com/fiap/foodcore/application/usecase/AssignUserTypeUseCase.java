package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.AssignUserTypeInput;

public interface AssignUserTypeUseCase {
    void execute(Long userId, AssignUserTypeInput input);
}
