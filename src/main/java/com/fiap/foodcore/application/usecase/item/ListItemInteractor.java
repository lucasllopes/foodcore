package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.mapper.ItemMapper;

public class ListItemInteractor {
    private final ItemGateway itemGateway;

    public ListItemInteractor(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public DomainPage<ItemCreateOutput> execute(PageRequestDomain pageRequest) {
        DomainPage<Item> domainPage = itemGateway.findAll(pageRequest);
        return domainPage.map(ItemMapper::fromDomain);
    }

}
