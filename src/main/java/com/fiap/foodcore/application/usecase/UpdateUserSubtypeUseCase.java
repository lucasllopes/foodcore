package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.UpdateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.output.UpdateUserSubtypeOutput;

public interface UpdateUserSubtypeUseCase {

    UpdateUserSubtypeOutput execute(Long id, UpdateUserSubtypeInput updateUserSubtypeInput);

}
