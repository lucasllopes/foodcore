package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;
import com.fiap.foodcore.infrastructure.security.UserDetailsAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class CreateMenuInteractor {

    private final MenuRepositoryGateway menuRepositoryGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateMenuInteractor(MenuRepositoryGateway menuRepositoryGateway, RestaurantGateway restaurantGateway) {
        this.menuRepositoryGateway = menuRepositoryGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuCreateOutput execute(CreateMenuInput createMenuInput) {


        var restaurante = validaSeExisteRestaurante(createMenuInput);

        validaSeNomeDuplicado(createMenuInput);

        Menu menu = new Menu.Builder()
                .description(createMenuInput.descricao())
                .name(createMenuInput.nome())
                .itemsList(
                        createMenuInput.items() == null ? List.of() :
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
                .restaurantId(restaurante)
                .build();

        var saveMenu = menuRepositoryGateway.save(menu);
        return new MenuCreateOutput(
                saveMenu.getId(),
                saveMenu.getName(),
                saveMenu.getDescription(),
                saveMenu.getRestaurantId().getId(),
                saveMenu.getItems() == null ? List.of() :
                        saveMenu.getItems().stream()
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

    private void validaSeNomeDuplicado(CreateMenuInput createMenuInput) {
        menuRepositoryGateway.findByNameAndRestaurantId(createMenuInput.nome(), createMenuInput.restaurantId())
                .ifPresent(menu -> {
                    throw new DuplicatedDataException("Cardápio com o nome '" + createMenuInput.nome() + "' ja existe para o restaurante com ID: " + createMenuInput.restaurantId());
                });
    }

    private Restaurant validaSeExisteRestaurante(CreateMenuInput createMenuInput) {
        var restaurante = restaurantGateway.findById(createMenuInput.restaurantId())
                .orElseThrow(() -> new DataNotFoundException("Restaurante não encontrado com ID: " + createMenuInput.restaurantId()));
        return restaurante;
    }

}
