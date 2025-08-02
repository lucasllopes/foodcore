package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.application.gateway.MenuGateway;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("menuSecurity")
public class MenuSecurity {

    private final MenuGateway menuGateway;

    public MenuSecurity(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    public boolean isOwner(Long menuId, Authentication authentication) {

        var menuNotFound = menuGateway.findById(menuId);
        if (menuNotFound.isEmpty()) {
            return true;
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        UserDetailsAdapter userDetails = (UserDetailsAdapter) authentication.getPrincipal();
        Long userId = userDetails.getId();

        boolean isOwner = menuGateway.findById(menuId)
                .map(menu -> menu.getRestaurantId().getOwnerId().equals(userId))
                .orElse(false);

        if (!isOwner) {
            throw new AccessDeniedException("Você só pode criar, atualizar ou deletar menus para seu restaurante.");
        }

        return true;
    }
}
