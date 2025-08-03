package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.input.UpdateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.infrastructure.mapper.ItemMapper;

public class UpdateItemInteractor {
    private final ItemGateway itemGateway;

    public UpdateItemInteractor(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public ItemCreateOutput execute(Long id, UpdateItemInput input) {
        var existing = itemGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Item não encontrado"));

        itemGateway.findByName(input.name())
                .filter(i -> !i.getId().equals(id))
                .ifPresent(i -> {
                    throw new DuplicatedDataException("Nome item já em uso");
                });

        var atualizado = existing.atualizarInformacoes(
                input.name(),
                input.description(),
                input.price(),
                input.availability(),
                input.photo()
        );

        var itemComOwnerId = new Item.Builder()
                .id(atualizado.getId())
                .name(atualizado.getName())
                .description(atualizado.getDescription())
                .price(atualizado.getPrice())
                .availability(atualizado.getAvailability())
                .photo(atualizado.getPhoto())
                .ownerId(existing.getOwnerId()) // Preservando o ownerId do item original
                .build();

        var saved = itemGateway.save(itemComOwnerId);
        return ItemMapper.fromDomain(saved);
    }
}
