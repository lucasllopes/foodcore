package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.application.gateway.UserGateway;

public class ListUserInteractor {

    private final UserGateway userGateway;

    public ListUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public DomainPage<CreateUserOutput> execute(PageRequestDomain pageRequest) {
        DomainPage<User> domainPage =
                userGateway.findAll(pageRequest);

        return domainPage.map(UserMapper::fromDomain);
    }

}
