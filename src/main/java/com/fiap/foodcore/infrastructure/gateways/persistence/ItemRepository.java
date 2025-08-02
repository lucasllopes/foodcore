package com.fiap.foodcore.infrastructure.gateways.persistence;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String name);
    List<ItemEntity> findByMenus_Id(Long menuId);
    List<ItemEntity> findByNameIgnoreCase(String name);
}
