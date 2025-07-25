package com.fiap.foodcore.infrastructure.gateways.persistence;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

  Optional<MenuEntity> findByName(String name);
  Optional<MenuEntity> findByNameAndRestaurantId(String name, Long restaurantId);
}
