package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;

public class CreateRestaurantInteractor{
    private final RestaurantGateway restaurantGateway;
    private final FindUserByIdInteractor findUserByIdInteractor;
    public CreateRestaurantInteractor(RestaurantGateway restaurantGateway, FindUserByIdInteractor findUserByIdInteractor){
        this.restaurantGateway = restaurantGateway;
        this.findUserByIdInteractor = findUserByIdInteractor;
    }
    public CreateRestaurantOutput execute(CreateRestaurantInput createRestaurantInput) {
        Restaurant restaurant = RestaurantMapper.toDomain(createRestaurantInput);
        this.validateRestaurant(restaurant);
        Restaurant savedRestaurant = this.restaurantGateway.save(restaurant);
        return RestaurantMapper.fromDomain(savedRestaurant);
    }
    private void validateRestaurant(Restaurant restaurant){
        this.findUserByIdInteractor.execute(restaurant.getOwnerId());
    }
}
