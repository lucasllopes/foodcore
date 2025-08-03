package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.domain.Menu;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("itemSecurity")
public class ItemSecurity {

    private final MenuGateway menuGateway;

    public ItemSecurity(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    public boolean isOwner(Long itemId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        UserDetailsAdapter userDetails = (UserDetailsAdapter) authentication.getPrincipal();
        Long userId = userDetails.getId();

        List<Menu> menus = menuGateway.findMenusByItemId(itemId);

        if (menus.isEmpty()) {
            throw new AccessDeniedException("Item não encontrado ou não associado a nenhum restaurante.");
        }

        boolean isOwner = menuGateway.findById(menus.getFirst().getId())
                .map(menu -> menu.getRestaurantId().getOwnerId().equals(userId))
                .orElse(false);

        if (!isOwner) {
            throw new AccessDeniedException("Você só pode editar/excluir itens do seu restaurante.");
        }

        return true;
    }
}
