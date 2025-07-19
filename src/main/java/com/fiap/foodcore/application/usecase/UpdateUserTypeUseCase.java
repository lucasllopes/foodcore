package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.UpdateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.UpdateUserTypeOutput;

public interface UpdateUserTypeUseCase {

    UpdateUserTypeOutput execute(Long id, UpdateUserTypeInput updateUserTypeInput);

}
