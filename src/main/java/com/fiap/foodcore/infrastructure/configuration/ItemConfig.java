package com.fiap.foodcore.infrastructure.configuration;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.item.*;
import com.fiap.foodcore.infrastructure.gateways.ItemRepositoryGateway;
import com.fiap.foodcore.infrastructure.gateways.persistence.ItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemConfig {

    @Bean
    ItemGateway itemGateway(ItemRepository itemRepository) {
        return new ItemRepositoryGateway(itemRepository);
    }

    @Bean
    ItemRepositoryGateway itemRepositoryGateway(ItemRepository itemRepository) {
        return new ItemRepositoryGateway(itemRepository);
    }

    @Bean
    CreateItemInteractor createItemInteractor(ItemRepositoryGateway itemRepositoryGateway, UserGateway userGateway) {
        return new CreateItemInteractor(itemRepositoryGateway, userGateway);
    }

    @Bean
    DeleteItemInteractor deleteItemInteractor(ItemGateway  itemGateway, MenuGateway menuGateway) {
        return new DeleteItemInteractor(itemGateway, menuGateway);
    }

    @Bean
    FindItemByIdInteractor findItemByIdInteractor(ItemGateway itemGateway) {
        return new FindItemByIdInteractor(itemGateway);
    }

    @Bean
    ListItemInteractor listItemInteractor(ItemRepositoryGateway itemRepositoryGateway) {
        return new ListItemInteractor(itemRepositoryGateway);
    }

    @Bean
    UpdateItemInteractor updateItemInteractor(ItemGateway  itemGateway) {
        return new UpdateItemInteractor(itemGateway);
    }

}
