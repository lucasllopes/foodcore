package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantAddressEntity;

import java.util.Optional;

public interface RestaurantGateway {

    Optional<Restaurant> findById(Long id);
    Optional<Restaurant> findByName(String name);
    DomainPage<Restaurant> findAll(PageRequestDomain pageRequest);
    Restaurant save(Restaurant restaurant);
    void delete(Long id);
}
