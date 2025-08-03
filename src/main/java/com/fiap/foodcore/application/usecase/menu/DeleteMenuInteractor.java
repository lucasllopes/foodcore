package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.MenuGateway;

public class DeleteMenuInteractor {
    private final MenuGateway menuGateway;
    private final ItemGateway itemGateway;

    public DeleteMenuInteractor(MenuGateway menuGateway, ItemGateway gateway) {
        this.menuGateway = menuGateway;
        this.itemGateway = gateway;
    }

    public void execute(Long id) {

       var menu = menuGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cardápio não encontrado"));


        var itemsVinculados = itemGateway.findItemsByMenuId(id);

        if(!itemsVinculados.isEmpty()) {
            String codeItems = itemsVinculados.stream()
                    .map(item -> " "+ item.getId() + " ")
                    .toList()
                    .toString();
            throw new DataNotFoundException("Cardápio não pode ser excluído, pois está vinculado aos itens: " + codeItems);
        }

        menuGateway.delete(menu);
    }
}
