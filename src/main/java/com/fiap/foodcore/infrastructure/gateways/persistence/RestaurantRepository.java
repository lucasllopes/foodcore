package com.fiap.foodcore.infrastructure.gateways.persistence;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
