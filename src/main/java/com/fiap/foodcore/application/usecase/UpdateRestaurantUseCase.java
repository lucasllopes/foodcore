package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public interface UpdateRestaurantUseCase {
    CreateRestaurantOutput execute(Long id, UpdateRestaurantInput input);
}
