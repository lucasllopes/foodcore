package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;

public class CreateMenuInteractor {

    private final MenuRepositoryGateway menuRepositoryGateway;

    public CreateMenuInteractor(MenuRepositoryGateway menuRepositoryGateway) {
        this.menuRepositoryGateway = menuRepositoryGateway;
    }

    public void execute(CreateMenuInput createMenuInput) {
        Menu menu = new Menu.Builder().
                name(createMenuInput.nome())
                .description(createMenuInput.descricao())
                .build();

        menuRepositoryGateway.save(menu);
    }

}
