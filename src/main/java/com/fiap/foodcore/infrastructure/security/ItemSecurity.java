package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.application.gateway.ItemGateway;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;



@Component("itemSecurity")
public class ItemSecurity {

    private final ItemGateway itemGateway;

    public ItemSecurity(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public boolean isOwner(Long itemId, Authentication authentication) {
        // Caso 1: Usuário não autenticado
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("Autenticação necessária para acessar o item #" + itemId);
        }

        if (!authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Credenciais inválidas para acessar o item #" + itemId);
        }

        UserDetailsAdapter userDetails = (UserDetailsAdapter) authentication.getPrincipal();
        Long userId = userDetails.getId();

        // Caso 2: Item não existe
        boolean itemExists = itemGateway.findById(itemId).isPresent();
        if (!itemExists) {
            throw new AccessDeniedException("Item #" + itemId + " não foi encontrado no sistema.");
        }

        // Caso 3: Usuário não é proprietário
        boolean isOwner = itemGateway.existsByIdAndOwnerId(itemId, userId);
        if (!isOwner) {
            throw new AccessDeniedException("Acesso negado ao item #" + itemId + ". Apenas o proprietário pode editar ou excluir este item.");
        }

        return true;
    }
}
