package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;

public class FindMenuByIdInteractor {
    private final MenuGateway menuGateway;

    public FindMenuByIdInteractor(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    public MenuCreateOutput execute(Long id) {
        var menu = menuGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cardápio não encontrado"));

        return new MenuCreateOutput(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getRestaurantId().getId(),
                menu.getItems().stream()
                        .map(item -> new ItemCreateOutput(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getPrice(),
                                item.getAvailability(),
                                item.getPhoto(),
                                item.getOwnerId().getId()))
                        .toList()
        );
    }

}
