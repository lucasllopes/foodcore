package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;
import com.fiap.foodcore.infrastructure.mapper.MenuMapper;

public class ListMenuInteractor {

    private final MenuGateway menuGateway;

    public ListMenuInteractor(MenuRepositoryGateway menuRepositoryGateway) {
        this.menuGateway = menuRepositoryGateway;
    }

    public DomainPage<MenuCreateOutput> execute(PageRequestDomain pageRequest) {
        DomainPage<Menu> domainPage = menuGateway.findAll(pageRequest);
        return domainPage.map(MenuMapper::fromDomain);
    }
}
