package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.AssignUserSubtypeInput;

public interface AssignUserTypeUseCase {
    void execute(Long userId, AssignUserSubtypeInput input);
}
