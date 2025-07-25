package com.fiap.foodcore.application.usecase.restaurant;

import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public interface FindRestaurantByIdUseCase {
    CreateRestaurantOutput execute(Long id);
}
