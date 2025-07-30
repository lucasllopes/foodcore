package com.fiap.foodcore.usecase.menu;


import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.usecase.menu.FindMenuByIdInteractor;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindMenuByIdInteractorTest {

    @Mock
    private MenuGateway menuGateway;

    private FindMenuByIdInteractor findMenuByIdInteractor;

    @BeforeEach
    void setUp() {
        findMenuByIdInteractor = new FindMenuByIdInteractor(menuGateway);
    }

    @Test
    @DisplayName("Deve encontrar um menu pelo ID quando o menu existe")
    void shouldFindMenuByIdWhenMenuExists() {
        Long menuId = 1L;
        Long restaurantId = 2L;
        Long itemId = 3L;

        String menuName = "Menu Test";
        String menuDescription = "Menu Description";

        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getId()).thenReturn(restaurantId);

        Item item = mock(Item.class);
        when(item.getId()).thenReturn(itemId);
        when(item.getName()).thenReturn("Item Name");
        when(item.getDescription()).thenReturn("Item Description");
        when(item.getPrice()).thenReturn(BigDecimal.valueOf(10.0));
        when(item.getAvailability()).thenReturn("LOCAL");
        when(item.getPhoto()).thenReturn("photo-url");

        Menu menu = mock(Menu.class);
        when(menu.getId()).thenReturn(menuId);
        when(menu.getName()).thenReturn(menuName);
        when(menu.getDescription()).thenReturn(menuDescription);
        when(menu.getRestaurantId()).thenReturn(restaurant);
        when(menu.getItems()).thenReturn(List.of(item));

        when(menuGateway.findById(menuId)).thenReturn(Optional.of(menu));

        MenuCreateOutput result = findMenuByIdInteractor.execute(menuId);

        assertNotNull(result);
        assertEquals(menuId, result.id());
        assertEquals(menuName, result.name());
        assertEquals(menuDescription, result.description());
        assertEquals(restaurantId, result.restaurantId());

        assertEquals(1, result.items().size());
        var resultItem = result.items().getFirst();
        assertEquals(itemId, resultItem.id());
        assertEquals("Item Name", resultItem.name());
        assertEquals("Item Description", resultItem.description());
        assertEquals(BigDecimal.valueOf(10.0), resultItem.price());
        assertEquals("LOCAL", resultItem.availability());
        assertEquals("photo-url", resultItem.photo());

        verify(menuGateway).findById(menuId);
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException quando o menu não for encontrado")
    void shouldThrowDataNotFoundExceptionWhenMenuNotFound() {
        Long menuId = 1L;

        when(menuGateway.findById(menuId)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> findMenuByIdInteractor.execute(menuId)
        );

        assertEquals("Menu não encontrado", exception.getMessage());

        verify(menuGateway).findById(menuId);
    }
}
