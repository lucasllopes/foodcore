package com.fiap.foodcore.application.usecase.interactor.restaurant;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.CreateRestaurantUseCase;
import com.fiap.foodcore.application.usecase.FindUserByIdUseCase;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;

public class CreateRestaurantInteractor implements CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public CreateRestaurantInteractor(RestaurantGateway restaurantGateway, FindUserByIdUseCase findUserByIdUseCase) {
        this.restaurantGateway = restaurantGateway;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @Override
    public CreateRestaurantOutput execute(CreateRestaurantInput createRestaurantInput) {
        Restaurant restaurant = RestaurantMapper.toDomain(createRestaurantInput);
        this.validateRestaurant(restaurant);
        Restaurant savedRestaurant = this.restaurantGateway.save(restaurant);
        return RestaurantMapper.fromDomain(savedRestaurant);
    }

    private void validateRestaurant(Restaurant restaurant) {
        this.findUserByIdUseCase.execute(restaurant.getOwnerId());
    }
}
