package com.fiap.foodcore.application.usecase.interactor.restaurant;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.restaurant.ListRestaurantUseCase;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

public class ListRestaurantInteractor implements ListRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public ListRestaurantInteractor(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public DomainPage<CreateRestaurantOutput> execute(String name, PageRequestDomain pageRequest) {
        DomainPage<Restaurant> restaurants = findRestaurants(name, pageRequest);
        return restaurants.map(RestaurantMapper::fromDomain);
    }
    private DomainPage<Restaurant> findRestaurants(String name, PageRequestDomain pageRequest) {
        if (name == null || name.isBlank()) {
            return restaurantGateway.findAll(pageRequest);
        }
        return restaurantGateway.findAllByName(name, pageRequest);
    }
}
