package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.mapper.MenuItemMapper;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuUpdateRequestDTO;

import java.util.List;

public class UpdateMenuInteractor {

    private final MenuGateway menuGateway;
    private final RestaurantGateway restaurantGateway;

    public UpdateMenuInteractor(MenuGateway menuGateway, RestaurantGateway restaurantGateway) {
        this.menuGateway = menuGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuCreateOutput execute(Long id, MenuUpdateRequestDTO menuUpdateRequestDTO) {
        var existingMenu = menuGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cardápio não encontrado com ID: "+ id));

        menuGateway.findByName(menuUpdateRequestDTO.name())
                .filter(menu -> !menu.getId().equals(id))
                .ifPresent(menu -> { throw new DuplicatedDataException("Nome de Cardápio já utilizado"); });

        restaurantGateway.findById(menuUpdateRequestDTO.restaurantId())
                .orElseThrow(() -> new DataNotFoundException("Restaurante não encontrado com  ID: " + menuUpdateRequestDTO.restaurantId()));

        List<Item> itensAtualizados = menuUpdateRequestDTO.items() == null ?
                java.util.Collections.emptyList() :
                menuUpdateRequestDTO.items().stream().map(MenuItemMapper::fromDto).toList();

        Menu menuToUpdate = existingMenu.atualizarInformacoes(
                menuUpdateRequestDTO.name(),
                menuUpdateRequestDTO.description(),
                menuUpdateRequestDTO.restaurantId(),
                itensAtualizados);

        Menu savedMenu = menuGateway.save(menuToUpdate);

        return new MenuCreateOutput(
                savedMenu.getId(),
                savedMenu.getName(),
                savedMenu.getDescription(),
                savedMenu.getRestaurantId().getId(),
                savedMenu.getItems() == null ? java.util.Collections.emptyList() :
                        savedMenu.getItems().stream().map(MenuItemMapper::toOutput).toList());
    }

}
