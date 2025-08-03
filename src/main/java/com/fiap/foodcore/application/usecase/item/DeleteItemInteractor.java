package com.fiap.foodcore.application.usecase.item;

import com.fiap.foodcore.application.exception.BusinessException;
import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.MenuGateway;

import java.awt.*;

public class DeleteItemInteractor {
    private final ItemGateway itemGateway;
    private final MenuGateway menuGateway;

    public DeleteItemInteractor(ItemGateway itemGateway, MenuGateway menuGateway) {
        this.itemGateway = itemGateway;
        this.menuGateway = menuGateway;
    }

    public void execute(Long id) {

        var item = itemGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Item não encontrado"));

        var menusVinculados = menuGateway.findMenusByItemId(id);

        if(!menusVinculados.isEmpty()) {
            String nomesMenus = menusVinculados.stream()
                    .map(menu -> menu.getName())
                    .toList()
                    .toString();
            throw new BusinessException("Item não pode ser excluído, pois está vinculado aos menus: " + nomesMenus);
        }

        itemGateway.delete(item.getId());
    }

}
