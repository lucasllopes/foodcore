package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.input.UpdateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
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

        var saved = itemGateway.save(atualizado);
        return ItemMapper.fromDomain(saved);
    }
}
