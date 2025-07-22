package com.fiap.foodcore.application.usecase.interactor.restaurant;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.restaurant.FindRestaurantByIdUseCase;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public class FindRestaurantByIdInteractor implements FindRestaurantByIdUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByIdInteractor(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public CreateRestaurantOutput execute(Long id) {
        return restaurantGateway.findById(id)
                .map(RestaurantMapper::fromDomain)
                .orElseThrow(() -> new DataNotFoundException("Restaurante não encontrado"));
    }
}
