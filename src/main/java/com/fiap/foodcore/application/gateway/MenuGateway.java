package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

import java.util.Optional;

public interface MenuGateway {

    Optional<Menu> findById(Long id);

    DomainPage<Menu> findAll(PageRequestDomain pageRequest);

    Menu save(Menu menu);

    void delete(Menu menu);

    Optional<Menu> findByName(String name);

    Optional<Menu> findByNameAndRestaurantId(String name, Long restaurantId);
}
