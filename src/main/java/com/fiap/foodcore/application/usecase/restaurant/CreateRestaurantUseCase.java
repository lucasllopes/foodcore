package com.fiap.foodcore.application.usecase.restaurant;

import com.fiap.foodcore.application.usecase.input.restaurant.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public interface CreateRestaurantUseCase {
    CreateRestaurantOutput execute(CreateRestaurantInput createRestaurantInput);
}
