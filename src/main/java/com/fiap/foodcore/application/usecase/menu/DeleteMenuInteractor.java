package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.infrastructure.gateways.persistence.MenuRepository;

public class DeleteMenuInteractor {
    private final MenuGateway menuGateway;

    public DeleteMenuInteractor(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    public void execute(Long id) {
       var menu = menuGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cardápio não encontrado"));
        menuGateway.delete(menu);
    }
}
