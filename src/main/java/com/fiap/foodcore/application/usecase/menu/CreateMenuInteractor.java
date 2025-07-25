package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;

public class CreateMenuInteractor {

    private final MenuRepositoryGateway menuRepositoryGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateMenuInteractor(MenuRepositoryGateway menuRepositoryGateway, RestaurantGateway restaurantGateway) {
        this.menuRepositoryGateway = menuRepositoryGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuCreateOutput execute(CreateMenuInput createMenuInput) {

        restaurantGateway.findById(createMenuInput.restaurantId())
                .orElseThrow(() -> new DataNotFoundException("Restaurant not found with ID: " + createMenuInput.restaurantId()));

        // Check for duplicate menu name
        menuRepositoryGateway.findByNameAndRestaurantId(createMenuInput.nome(), createMenuInput.restaurantId())
                .ifPresent(menu -> {
                    throw new DuplicatedDataException("Menu with name '" + createMenuInput.nome() + "' already exists for restaurant ID: " + createMenuInput.restaurantId());
                });

        Menu menu = new Menu.Builder()
                .description(createMenuInput.descricao())
                .name(createMenuInput.nome())
                .itemsList(
                        createMenuInput.items().stream()
                                .map(item -> new Item.Builder()
                                        .name(item.name())
                                        .description(item.description())
                                        .price(item.price())
                                        .availability(item.availability())
                                        .photo(item.photo())
                                        .build()
                                ).toList()
                )
                .restaurantId(createMenuInput.restaurantId())
                .build();

        menuRepositoryGateway.save(menu);
        return new MenuCreateOutput(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getRestaurantId(),
                menu.getItems().stream()
                        .map(item -> new ItemCreateOutput(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getPrice(),
                                item.getAvailability(),
                                item.getPhoto()
                        )).toList()
        );
    }

}
