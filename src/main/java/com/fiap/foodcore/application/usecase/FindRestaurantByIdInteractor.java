package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.exception.RestaurantNotFoundException;

public class FindRestaurantByIdInteractor {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByIdInteractor(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public CreateRestaurantOutput execute(Long id) {
        return restaurantGateway.findById(id)
                .map(RestaurantMapper::fromDomain)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado"));
    }
}
