package com.fiap.foodcore.application.usecase.interactor.user;

import com.fiap.foodcore.application.usecase.ListUserUseCase;
import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.application.gateway.UserGateway;

public class ListUserInteractor implements ListUserUseCase {

    private final UserGateway userGateway;

    public ListUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public DomainPage<CreateUserOutput> execute(PageRequestDomain pageRequest) {
        DomainPage<User> domainPage =
                userGateway.findAllPage(pageRequest);

        return domainPage.map(UserMapper::fromDomain);
    }

}
