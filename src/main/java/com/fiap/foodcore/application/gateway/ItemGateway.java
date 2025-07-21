package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.Item;

import java.util.Optional;

public interface ItemGateway {


    Optional<Item> findById(Long id);
    Item save(Item item);
    void delete(Item item);
    Optional<Item> findByName(String name);

}
