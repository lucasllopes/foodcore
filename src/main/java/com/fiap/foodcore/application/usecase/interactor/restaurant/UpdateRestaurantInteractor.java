package com.fiap.foodcore.application.usecase.interactor.restaurant;

import com.fiap.foodcore.application.exception.BusinessException;
import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.restaurant.UpdateRestaurantUseCase;
import com.fiap.foodcore.application.usecase.input.restaurant.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;

public class UpdateRestaurantInteractor implements UpdateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public UpdateRestaurantInteractor(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public CreateRestaurantOutput execute(Long id, UpdateRestaurantInput input) {
        var existing = this.restaurantGateway.findById(id).orElseThrow(() -> new DataNotFoundException("Restaurante não encontrado."));

        validateRestaurant(input.nome());
        var restaurant = RestaurantMapper.toDomain(existing, input);
        Restaurant savedRestaurant1 = restaurantGateway.save(restaurant);
        return RestaurantMapper.fromDomain(savedRestaurant1);
    }
    private void validateRestaurant(String restaurantName){
        this.restaurantGateway.findByName(restaurantName).ifPresent(existingRestaurant -> {throw new DuplicatedDataException("Já existe um restaurante cadastrado com este nome.");});
    }
}
