package com.fiap.foodcore.application.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.mapper.MenuItemMapper;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuUpdateRequestDTO;

import java.util.List;

public class UpdateMenuInteractor {

    private final MenuGateway menuGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public UpdateMenuInteractor(MenuGateway menuGateway, RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.menuGateway = menuGateway;
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public MenuCreateOutput execute(Long id, MenuUpdateRequestDTO menuUpdateRequestDTO) {

        var existingMenu = menuGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cardápio não encontrado com ID: "+ id));

        menuGateway.findByName(menuUpdateRequestDTO.name())
                .filter(menu -> !menu.getId().equals(id))
                .ifPresent(menu -> { throw new DuplicatedDataException("Nome de Cardápio já utilizado"); });

        var restaurant = restaurantGateway.findById(menuUpdateRequestDTO.restaurantId())
                .orElseThrow(() -> new DataNotFoundException("Restaurante não encontrado com  ID: " + menuUpdateRequestDTO.restaurantId()));

        var restaurantOwner = userGateway.findById(restaurant.getOwnerId())
                .orElseThrow(() -> new DataNotFoundException("Dono do restaurante não encontrado com ID: " + restaurant.getOwnerId()));

        List<Item> itensAtualizados = menuUpdateRequestDTO.items() == null ?
                java.util.Collections.emptyList() :
                menuUpdateRequestDTO.items().stream()
                        .map(itemDto -> {
                            // Cria o item básico através do mapper
                            Item item = MenuItemMapper.fromDto(itemDto, restaurantOwner);
                            // Garante que o item tenha um proprietário definido
                            return new Item.Builder()
                                    .id(item.getId())
                                    .name(item.getName())
                                    .description(item.getDescription())
                                    .price(item.getPrice())
                                    .availability(item.getAvailability())
                                    .photo(item.getPhoto())
                                    .ownerId(restaurantOwner) // Define o proprietário do restaurante como dono do item
                                    .build();
                        })
                        .toList();

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
