package com.fiap.foodcore.infrastructure.gateways.persistence;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserSubtypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserSubtypeRepository extends JpaRepository<UserSubtypeEntity, Long> {

    @Query("SELECT u FROM UserSubtypeEntity u WHERE UPPER(u.name) = UPPER(:name)")
    List<UserSubtypeEntity> findByNameIgnoreCase(@Param("name") String name);
}
