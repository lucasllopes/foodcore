package com.fiap.foodcore.infrastructure.configuration;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.menu.*;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;
import com.fiap.foodcore.infrastructure.gateways.persistence.ItemRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.MenuRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuConfig {

    @Bean
    MenuGateway menuGateway(MenuRepository menuRepository, ItemRepository itemRepository) {
        return new MenuRepositoryGateway(menuRepository, itemRepository);
    }

    @Bean
    MenuRepositoryGateway menuRepositoryGateway(MenuRepository menuRepository, ItemRepository itemRepository) {
        return new MenuRepositoryGateway(menuRepository, itemRepository);
    }

    @Bean
    public CreateMenuInteractor createMenuInteractor(MenuRepositoryGateway menuRepositoryGateway,
                                                    RestaurantGateway restaurantGateway) {
        return new CreateMenuInteractor(menuRepositoryGateway, restaurantGateway);
    }

    @Bean
    public ListMenuInteractor listMenuInteractor(MenuRepositoryGateway menuRepositoryGateway) {
        return new ListMenuInteractor(menuRepositoryGateway);
    }

    @Bean
    public UpdateMenuInteractor updateMenuInteractor(MenuGateway menuGateway, RestaurantGateway restaurantGateway, UserGateway gateway) {
        return new UpdateMenuInteractor(menuGateway, restaurantGateway, gateway);
    }

    @Bean
    public DeleteMenuInteractor deleteMenuInteractor(MenuGateway menuGateway, ItemGateway itemGateway) {
        return new DeleteMenuInteractor(menuGateway, itemGateway);
    }

    @Bean
    public FindMenuByIdInteractor findMenuByIdInteractor(MenuGateway menuGateway) {
        return new FindMenuByIdInteractor(menuGateway);
    }

}
