package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {

    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    List<Restaurant> findAll();
    void delete(Long id);
}
