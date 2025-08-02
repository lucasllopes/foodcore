package com.fiap.foodcore.infrastructure.security;

import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantSecurityTest {

    private static final long OWNER_ID_AUTENTICATED = 1L;
    private static final long OWNER_ID_UNAUTENTICATED = 2L;
    private RestaurantGateway restaurantGateway;
    private RestaurantSecurity restaurantSecurity;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        restaurantSecurity = new RestaurantSecurity(restaurantGateway);
        restaurant = mock(Restaurant.class);
    }

    @Test
    void shouldReturnFalseWhenAuthenticationIsNull() {
        boolean result = restaurantSecurity.isOwner(1L, null);
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenUserIsNotAuthenticated() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        boolean result = restaurantSecurity.isOwner(1L, authentication);
        assertFalse(result);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        Authentication authentication = mockAuthenticatedUser(OWNER_ID_AUTENTICATED);

        when(restaurantGateway.findById(1L)).thenReturn(Optional.empty());

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                restaurantSecurity.isOwner(1L, authentication)
        );

        assertEquals("Você só pode atualizar/deletar restaurantes que pertencem a você.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantBelongsToAnotherUser() {
        Authentication authentication = mockAuthenticatedUser(OWNER_ID_AUTENTICATED);
        when(restaurant.getOwnerId()).thenReturn(OWNER_ID_UNAUTENTICATED);

        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(restaurant));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () ->
                restaurantSecurity.isOwner(1L, authentication)
        );

        assertEquals("Você só pode atualizar/deletar restaurantes que pertencem a você.", exception.getMessage());
    }

    @Test
    void shouldReturnTrueWhenUserIsOwner() {
        Authentication authentication = mockAuthenticatedUser(OWNER_ID_AUTENTICATED);


        when(restaurant.getOwnerId()).thenReturn(1L);

        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(restaurant));

        boolean result = restaurantSecurity.isOwner(1L, authentication);

        assertTrue(result);
    }

    private Authentication mockAuthenticatedUser(Long userId) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);

        UserDetailsAdapter userDetails = mock(UserDetailsAdapter.class);
        when(userDetails.getId()).thenReturn(userId);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        return authentication;
    }
}
