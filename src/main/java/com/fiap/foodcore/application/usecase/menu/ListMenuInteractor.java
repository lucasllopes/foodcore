package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.usecase.output.MenuOutput;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;
import com.fiap.foodcore.infrastructure.mapper.MenuMapper;

public class ListMenuInteractor {

    private final MenuRepositoryGateway menuRepositoryGateway;

    public ListMenuInteractor(MenuRepositoryGateway menuRepositoryGateway) {
        this.menuRepositoryGateway = menuRepositoryGateway;
    }

    public DomainPage<MenuOutput> execute(PageRequestDomain pageRequest) {
        DomainPage<Menu> domainPage = menuRepositoryGateway.findAll(pageRequest);
        return domainPage.map(MenuMapper::fromDomain);
    }
}
