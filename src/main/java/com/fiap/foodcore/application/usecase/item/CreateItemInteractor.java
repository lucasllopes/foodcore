package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import jakarta.persistence.EntityNotFoundException;

public class CreateItemInteractor {

    private final ItemGateway itemGateway;
    private final UserGateway userGateway;

    public CreateItemInteractor(ItemGateway itemGateway, UserGateway userGateway) {
        this.itemGateway = itemGateway;
        this.userGateway = userGateway;
    }

    public ItemCreateOutput execute(CreateItemInput createItemInput) {

        itemGateway.findByName(createItemInput.name())
                .ifPresent(i -> {
                    throw new DuplicatedDataException("Nome item já em uso");
                });

        // Validar se o proprietário existe
        if (createItemInput.ownerId() == null) {
            throw new IllegalArgumentException("ID do proprietário não pode ser nulo");
        }

        userGateway.findById(createItemInput.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado com ID: " + createItemInput.ownerId()));

        var item = new Item.Builder()
                .name(createItemInput.name())
                .description(createItemInput.description())
                .price(createItemInput.price())
                .availability(createItemInput.availability())
                .photo(createItemInput.photo())
                .ownerId(createItemInput.ownerId())
                .build();

        var savedItem = itemGateway.save(item);

        return new ItemCreateOutput(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getDescription(),
                savedItem.getPrice(),
                savedItem.getAvailability(),
                savedItem.getPhoto(),
                savedItem.getOwnerId()
        );
    }

}
