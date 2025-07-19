package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.ChangePasswordInput;

public interface ChangePasswordUseCase {
    void execute(Long id, ChangePasswordInput input);
}
