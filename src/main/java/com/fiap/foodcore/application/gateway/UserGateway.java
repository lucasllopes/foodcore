package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

import java.util.Optional;

public interface UserGateway {

    Optional<User> findById(Long id);
    DomainPage<User> findAllPage(PageRequestDomain pageRequest);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(User user);
    boolean existsBySubType(Long id);
}
