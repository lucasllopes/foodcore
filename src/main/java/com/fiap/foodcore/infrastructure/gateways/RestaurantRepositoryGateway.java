package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.gateways.persistence.RestaurantRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import com.fiap.foodcore.infrastructure.mapper.RestaurantEntityMapper;

import java.util.List;
import java.util.Optional;

public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    public RestaurantRepositoryGateway(RestaurantRepository restaurantRepository, RestaurantEntityMapper restaurantEntityMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantEntityMapper = restaurantEntityMapper;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = restaurantEntityMapper.toEntity(restaurant);
        RestaurantEntity createdRestaurant = restaurantRepository.save(entity);
        return restaurantEntityMapper.toDomain(createdRestaurant);
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Restaurant> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
