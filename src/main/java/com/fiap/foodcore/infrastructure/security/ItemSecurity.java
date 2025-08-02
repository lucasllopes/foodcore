package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.domain.Menu;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("itemSecurity")
public class ItemSecurity {

    private final MenuGateway menuGateway;

    public ItemSecurity(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    public boolean isOwner(Long itemId, Authentication authentication) {
        List<Menu> menus = menuGateway.findMenusByItemId(itemId);

        if (menus.isEmpty()) {
            return true;
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Long ownerId = menus.getFirst().getRestaurantId().getOwnerId();
        Long userId = ((UserDetailsAdapter) authentication.getPrincipal()).getId();

        if (!ownerId.equals(userId)) {
            throw new AccessDeniedException("Você só pode editar/excluir itens do seu restaurante.");
        }

        return true;
    }
}
