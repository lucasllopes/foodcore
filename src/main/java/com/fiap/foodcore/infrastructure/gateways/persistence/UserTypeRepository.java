package com.fiap.foodcore.infrastructure.gateways.persistence;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {

    @Query("SELECT u FROM UserTypeEntity u WHERE UPPER(u.name) = UPPER(:name)")
    Optional<UserTypeEntity> findByNameIgnoreCase(@Param("name") String name);
}
