package com.fiap.foodcore.application.usecase.interactor;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.FindRestaurantByIdUseCase;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.exception.RestaurantNotFoundException;

public class FindRestaurantByIdInteractor implements FindRestaurantByIdUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByIdInteractor(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public CreateRestaurantOutput execute(Long id) {
        return restaurantGateway.findById(id)
                .map(RestaurantMapper::fromDomain)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado"));
    }
}
