package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;

public class CreateMenuInteractor {

    private final MenuRepositoryGateway menuRepositoryGateway;

    public CreateMenuInteractor(MenuRepositoryGateway menuRepositoryGateway) {
        this.menuRepositoryGateway = menuRepositoryGateway;
    }

    public MenuCreateOutput execute(CreateMenuInput createMenuInput) {
        Menu menu = new Menu.Builder()
                .description(createMenuInput.descricao()).
                name(createMenuInput.nome())
                .build();

        menuRepositoryGateway.save(menu);
        return new MenuCreateOutput(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                null
        );
    }

}
