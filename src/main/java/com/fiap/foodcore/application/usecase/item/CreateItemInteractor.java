package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateItemInteractor {
    private final static Logger logger = LoggerFactory.getLogger(CreateItemInteractor.class);
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

       var user =  userGateway.findById(createItemInput.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado com ID: " + createItemInput.ownerId()));

        logger.info("Tipo User encontrado: {}", user.getTipo());

        var item = new Item.Builder()
                .name(createItemInput.name())
                .description(createItemInput.description())
                .price(createItemInput.price())
                .availability(createItemInput.availability())
                .photo(createItemInput.photo())
                .ownerId(user)
                .build();

        var savedItem = itemGateway.save(item);

        return new ItemCreateOutput(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getDescription(),
                savedItem.getPrice(),
                savedItem.getAvailability(),
                savedItem.getPhoto(),
                savedItem.getOwnerId().getId()
        );
    }

}
