package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public interface FindRestaurantByIdUseCase {
    CreateRestaurantOutput execute(Long id);
}
