package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.gateway.ItemGateway;

public class DeleteItemInteractor {
    private final ItemGateway itemGateway;

    public DeleteItemInteractor(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public void execute(Long id) {
        var item = itemGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        itemGateway.delete(item);
    }

}
