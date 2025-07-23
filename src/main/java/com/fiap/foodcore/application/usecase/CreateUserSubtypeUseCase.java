package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.CreateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;

public interface CreateUserSubtypeUseCase {

    CreateUserSubtypeOutput execute(CreateUserSubtypeInput input);
}
