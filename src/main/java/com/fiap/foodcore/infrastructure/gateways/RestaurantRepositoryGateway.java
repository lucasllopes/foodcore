package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.exception.RestaurantNotFoundException;
import com.fiap.foodcore.infrastructure.gateways.persistence.RestaurantRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import com.fiap.foodcore.infrastructure.mapper.RestaurantEntityMapper;

import java.util.List;
import java.util.Optional;

public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantRepository restaurantRepository;

    public RestaurantRepositoryGateway(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = RestaurantEntityMapper.toEntity(restaurant);
        RestaurantEntity createdRestaurant = restaurantRepository.save(entity);
        return RestaurantEntityMapper.toDomain(createdRestaurant);
    }

    @Override
    public Optional<Restaurant> findById(Long restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurante com id " + restaurantId + " não encontrado."));
        return Optional.of(RestaurantEntityMapper.toDomain(restaurant));
    }

    @Override
    public List<Restaurant> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
