package com.fiap.foodcore.infrastructure.configuration;

import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.usecase.menu.CreateMenuInteractor;
import com.fiap.foodcore.application.usecase.menu.ListMenuInteractor;
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
    public CreateMenuInteractor createMenuInteractor(MenuRepositoryGateway menuRepositoryGateway) {
        return new CreateMenuInteractor(menuRepositoryGateway);
    }

    @Bean
    public ListMenuInteractor listMenuInteractor(MenuRepositoryGateway menuRepositoryGateway) {
        return new ListMenuInteractor(menuRepositoryGateway);
    }
}
