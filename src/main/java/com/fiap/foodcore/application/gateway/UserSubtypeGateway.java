package com.fiap.foodcore.application.gateway;

import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import java.util.List;
import java.util.Optional;

public interface UserSubtypeGateway {

    UserSubtype save(UserSubtype userSubtype);
    Optional<UserSubtype> findById(Long id);
    DomainPage<UserSubtype> findAll(PageRequestDomain pageRequest);
    void delete(UserSubtype user);
    DomainPage<UserSubtype> findAllPage(PageRequestDomain pageRequest);
    List<UserSubtype> findByNameIgnoreCase(String name);
}
