package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.usecase.mapper.MenuItemMapper;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuUpdateRequestDTO;

public class UpdateMenuInteractor {

    private final MenuGateway menuGateway;

    public UpdateMenuInteractor(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    public MenuCreateOutput execute(Long id, MenuUpdateRequestDTO menuUpdateRequestDTO) {
        var existingMenu = menuGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Menu not found"));

        // Check for duplicate name
        menuGateway.findByName(menuUpdateRequestDTO.name())
                .filter(menu -> !menu.getId().equals(id))
                .ifPresent(menu -> { throw new DuplicatedDataException("Menu name already in use"); });

        Menu menuToUpdate = existingMenu.atualizarInformacoes(
                menuUpdateRequestDTO.name(),
                menuUpdateRequestDTO.description(),
                menuUpdateRequestDTO.items().stream().map(MenuItemMapper::fromDto).toList());

        // Save the updated menu
        Menu savedMenu = menuGateway.save(menuToUpdate);

        return new MenuCreateOutput(
                savedMenu.getId(),
                savedMenu.getName(),
                savedMenu.getDescription(),
                savedMenu.getRestaurantId(),
                savedMenu.getItems().stream()
                        .map(MenuItemMapper::toOutput).toList());
    }

}
