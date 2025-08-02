package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("restaurantSecurity")
public class RestaurantSecurity {

    private final RestaurantGateway restaurantGateway;

    public RestaurantSecurity(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public boolean isOwner(Long restaurantId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        UserDetailsAdapter userDetails = (UserDetailsAdapter) authentication.getPrincipal();
        Long userId = userDetails.getId();

        boolean isOwner = restaurantGateway.findById(restaurantId)
                .map(restaurant -> restaurant.getOwnerId().equals(userId))
                .orElse(false);

        if (!isOwner) {
            throw new AccessDeniedException("Você só pode atualizar/deletar restaurantes que pertencem a você.");
        }

        return true;
    }
}
