package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

public class ListRestaurantInteractor {

    private final RestaurantGateway restaurantGateway;

    public ListRestaurantInteractor(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public DomainPage<CreateRestaurantOutput> execute(PageRequestDomain pageRequest) {
        DomainPage<Restaurant> domainPage =
                restaurantGateway.findAll(pageRequest);

        return domainPage.map(RestaurantMapper::fromDomain);
    }
}
