package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.DomainPage;
import com.fiap.foodcore.domain.PageRequestDomain;
import com.fiap.foodcore.domain.User;

import java.util.Optional;

public interface UserGateway {

    Optional<User> findById(Long id);
    DomainPage<User> findAll(PageRequestDomain pageRequest);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(User user);


}
