package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.UserType;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

import java.util.Optional;

public interface UserTypeGateway {

    UserType save(UserType userType);
    Optional<UserType> findById(Long id);
    DomainPage<UserType> findAll(PageRequestDomain pageRequest);
    void delete(UserType user);
}
