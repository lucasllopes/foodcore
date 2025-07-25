package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.gateways.persistence.RestaurantRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.mapper.RestaurantEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
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
        return restaurantRepository.findByNameIgnoreCase(name)
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
        return new DomainPage<>(items, 0,20,springPage.getTotalElements());
    }
    @Override
    public DomainPage<Restaurant> findAllByName(String name, PageRequestDomain pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.page(), pageRequest.size(), toSpringSort(pageRequest.sortOrders()));
        Page<RestaurantEntity> paginaSpring = restaurantRepository.findByNameContainingIgnoreCase(name, pageable);

        var items = paginaSpring.getContent().stream()
                .map(RestaurantEntityMapper::toDomain)
                .toList();

        return new DomainPage<>(
                items,
                paginaSpring.getNumber(),
                paginaSpring.getSize(),
                paginaSpring.getTotalElements()
        );
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

    private Sort toSpringSort(List<SortOrder> orders) {
        return Sort.by(
                orders.stream().map(
                        order -> order.isAscending() ?
                                Sort.Order.asc(order.getProperty()) : Sort.Order.desc(order.getProperty())
                ).toList());
    }
}
