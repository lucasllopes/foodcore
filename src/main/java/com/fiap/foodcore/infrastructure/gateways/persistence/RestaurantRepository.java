package com.fiap.foodcore.infrastructure.gateways.persistence;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Query("SELECT r FROM RestaurantEntity r WHERE r.name LIKE %:name%")
    Optional<RestaurantEntity> findByName(@Param("name") String name);
    @Query("SELECT r FROM RestaurantEntity r WHERE r.name LIKE %:name%")
    Page<RestaurantEntity> findAllByName(@Param("name") String name, Pageable pageable);
}
