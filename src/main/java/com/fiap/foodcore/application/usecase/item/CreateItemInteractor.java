package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;

public class CreateItemInteractor {


    private final ItemGateway itemGateway;

    public CreateItemInteractor(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public ItemCreateOutput execute(CreateItemInput createItemInput) {

        itemGateway.findByName(createItemInput.name())
                .ifPresent(i -> {
                    throw new DuplicatedDataException("Nome item já em uso");
                });

        var item = new Item.Builder()
                .name(createItemInput.name())
                .description(createItemInput.description())
                .price(createItemInput.price())
                .availability(createItemInput.availability())
                .photo(createItemInput.photo())
                .build();

        itemGateway.save(item);

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
