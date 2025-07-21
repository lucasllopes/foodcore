package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.infrastructure.gateways.ItemRepositoryGateway;

public class CreateItemInteractor {

    private final ItemRepositoryGateway itemRepositoryGateway;

    public CreateItemInteractor(ItemRepositoryGateway itemRepositoryGateway) {
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public ItemOutput execute(CreateItemInput createItemInput) {
        var item = new Item.Builder()
                .name(createItemInput.name())
                .description(createItemInput.description())
                .price(createItemInput.price())
                .availability(createItemInput.availability())
                .photo(createItemInput.photo())
                .build();

        itemRepositoryGateway.save(item);

        return new ItemOutput(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailability(),
                item.getPhoto()
        );
    }

}
