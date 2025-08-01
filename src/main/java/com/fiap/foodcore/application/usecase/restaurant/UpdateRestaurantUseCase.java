package com.fiap.foodcore.application.usecase.restaurant;

import com.fiap.foodcore.application.usecase.input.restaurant.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public interface UpdateRestaurantUseCase {
    CreateRestaurantOutput execute(Long id, UpdateRestaurantInput input, Long currentUserId);
}
