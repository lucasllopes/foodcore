package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.exception.RestaurantNotFoundException;

public class UpdateRestaurantInteractor {

    private final RestaurantGateway restaurantGateway;

    public UpdateRestaurantInteractor(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public CreateRestaurantOutput execute(Long id, UpdateRestaurantInput input) {
        var existing = this.restaurantGateway.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado"));

        var restaurant = RestaurantMapper.toDomain(existing, input);
        Restaurant savedRestaurant1 = restaurantGateway.save(restaurant);
        return RestaurantMapper.fromDomain(savedRestaurant1);
    }
}
