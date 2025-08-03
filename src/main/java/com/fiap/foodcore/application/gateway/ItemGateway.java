package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

import java.util.List;
import java.util.Optional;

public interface ItemGateway {
    DomainPage<Item> findAll(PageRequestDomain pageRequest);
    Optional<Item> findById(Long id);
    Item save(Item item);
    void delete(Long id);
    Optional<Item> findByName(String name);
    List<Item> findItemsByMenuId(Long menuId);
    Optional<Item> findByNameIgnoreCase(String name);

}
