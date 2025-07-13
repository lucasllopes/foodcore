package com.fiap.foodcore.infrastructure.gateways.persistence;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
}
