package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public class DeleteRestaurantInteractor {
    private final RestaurantGateway restaurantGateway;
    private final FindRestaurantByIdInteractor findRestaurantByIdInteractor;

    public DeleteRestaurantInteractor(RestaurantGateway restaurantGateway, FindRestaurantByIdInteractor findRestaurantByIdInteractor) {
        this.restaurantGateway = restaurantGateway;
        this.findRestaurantByIdInteractor = findRestaurantByIdInteractor;
    }
    public void execute(Long id) {
        CreateRestaurantOutput restaurant = findRestaurantByIdInteractor.execute(id);
        restaurantGateway.delete(restaurant.id());
    }
}
