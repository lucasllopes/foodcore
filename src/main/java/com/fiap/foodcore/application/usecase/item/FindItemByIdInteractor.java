package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;

public class FindItemByIdInteractor {
    private final ItemGateway itemGateway;

    public FindItemByIdInteractor(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public ItemCreateOutput execute(Long id) {
        var item = itemGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Item não encontrado"));

        return new ItemCreateOutput(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailability(),
                item.getPhoto()
        );
    }
}
