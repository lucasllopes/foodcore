package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public interface CreateRestaurantUseCase {
    CreateRestaurantOutput execute(CreateRestaurantInput createRestaurantInput);
}
