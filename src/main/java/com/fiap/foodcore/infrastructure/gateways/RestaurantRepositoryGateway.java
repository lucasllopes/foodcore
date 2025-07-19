package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.RestaurantRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import com.fiap.foodcore.infrastructure.mapper.RestaurantEntityMapper;

import java.util.Optional;

public class RestaurantRepositoryGateway implements RestaurantGateway {

    private final RestaurantRepository restaurantRepository;

    public RestaurantRepositoryGateway(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Optional<Restaurant> findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(RestaurantEntityMapper::toDomain);
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        return restaurantRepository.findByName(name)
                .map(RestaurantEntityMapper::toDomain);
    }

    @Override
    public DomainPage<Restaurant> findAll(PageRequestDomain pageRequest) {
        var springPage = restaurantRepository.findAll(
                org.springframework.data.domain.PageRequest
                        .of(pageRequest.page(), pageRequest.size())
        );
        var items = springPage.getContent().stream()
                .map(RestaurantEntityMapper::toDomain)
                .toList();
        return new DomainPage<>(items, springPage.getTotalElements());
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = RestaurantEntityMapper.toEntity(restaurant);
        RestaurantEntity createdRestaurant = restaurantRepository.save(entity);
        return RestaurantEntityMapper.toDomain(createdRestaurant);
    }

    @Override
    public void delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
