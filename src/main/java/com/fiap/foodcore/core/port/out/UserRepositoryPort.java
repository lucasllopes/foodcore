package com.fiap.foodcore.core.port.out;

import com.fiap.foodcore.core.model.domain.DomainPage;
import com.fiap.foodcore.core.model.domain.PageRequestDomain;
import com.fiap.foodcore.core.model.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findById(Long id);
    DomainPage<User> findAll(PageRequestDomain pageRequest);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(User user);
}
