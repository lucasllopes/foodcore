package com.fiap.foodcore.application.usecase.interactor;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.DeleteRestaurantUseCase;
import com.fiap.foodcore.application.usecase.FindRestaurantByIdUseCase;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;

public class DeleteRestaurantInteractor implements DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    public DeleteRestaurantInteractor(RestaurantGateway restaurantGateway, FindRestaurantByIdUseCase findRestaurantByIdUseCase) {
        this.restaurantGateway = restaurantGateway;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
    }

    @Override
    public void execute(Long id) {
        CreateRestaurantOutput restaurant = findRestaurantByIdUseCase.execute(id);
        restaurantGateway.delete(restaurant.id());
    }
}
