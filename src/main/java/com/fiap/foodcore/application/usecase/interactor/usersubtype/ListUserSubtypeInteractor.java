package com.fiap.foodcore.application.usecase.interactor.usersubtype;

import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.ListUserSubtypeUseCase;
import com.fiap.foodcore.application.usecase.mapper.UserSubtypeMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

public class ListUserSubtypeInteractor implements ListUserSubtypeUseCase {

    private final UserSubtypeGateway gateway;

    public ListUserSubtypeInteractor(UserSubtypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public DomainPage<CreateUserSubtypeOutput> execute(PageRequestDomain pageRequest) {
        DomainPage<UserSubtype> domainPage =
                gateway.findAllPage(pageRequest);

        return domainPage.map(UserSubtypeMapper::fromDomain);
    }
}
