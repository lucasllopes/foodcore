package com.fiap.foodcore.usecase.menu;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
import com.fiap.foodcore.application.usecase.menu.CreateMenuInteractor;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMenuInteractorTest {

    @Mock
    private MenuRepositoryGateway menuRepositoryGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    private CreateMenuInteractor createMenuInteractor;

    @BeforeEach
    void setUp() {
        createMenuInteractor = new CreateMenuInteractor(menuRepositoryGateway, restaurantGateway);
    }

    @Test
    @DisplayName("Deve criar um menu com sucesso quando todos os dados são válidos")
    void shouldCreateMenuSuccessfully() {
        Long restaurantId = 1L;
        Long menuId = 2L;
        Long itemId = 3L;

        String menuName = "Menu Test";
        String menuDescription = "Menu Description";

        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getId()).thenReturn(restaurantId);
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        when(menuRepositoryGateway.findByNameAndRestaurantId(menuName, restaurantId))
                .thenReturn(Optional.empty());

        Item savedItem = mock(Item.class);
        when(savedItem.getId()).thenReturn(itemId);
        when(savedItem.getName()).thenReturn("Item Name");
        when(savedItem.getDescription()).thenReturn("Item Description");
        when(savedItem.getPrice()).thenReturn(BigDecimal.valueOf(10.0));
        when(savedItem.getAvailability()).thenReturn("LOCAL");
        when(savedItem.getPhoto()).thenReturn("photo-url");

        Menu savedMenu = mock(Menu.class);
        when(savedMenu.getId()).thenReturn(menuId);
        when(savedMenu.getName()).thenReturn(menuName);
        when(savedMenu.getDescription()).thenReturn(menuDescription);
        when(savedMenu.getRestaurantId()).thenReturn(restaurant);
        when(savedMenu.getItems()).thenReturn(List.of(savedItem));

        when(menuRepositoryGateway.save(any(Menu.class))).thenReturn(savedMenu);

        var itemInput = new CreateItemInput(
                "Item Name",
                "Item Description",
                BigDecimal.valueOf(10.0),
                "LOCAL",
                "photo-url"
        );

        var createMenuInput = new CreateMenuInput(
                menuName,
                menuDescription,
                restaurantId,
                List.of(itemInput)
        );


        var result = createMenuInteractor.execute(createMenuInput);

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

        verify(restaurantGateway).findById(restaurantId);
        verify(menuRepositoryGateway).findByNameAndRestaurantId(menuName, restaurantId);
        verify(menuRepositoryGateway).save(any(Menu.class));
    }

    @Test
    @DisplayName("Deve lançar DataNotFoundException quando o restaurante não for encontrado")
    void shouldThrowDataNotFoundExceptionWhenRestaurantNotFound() {

        Long restaurantId = 1L;

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        var createMenuInput = new CreateMenuInput(
                "Menu Name",
                "Menu Description",
                restaurantId,
                List.of()
        );


        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> createMenuInteractor.execute(createMenuInput)
        );

        assertEquals("Restaurant not found with ID: " + restaurantId, exception.getMessage());

        verify(restaurantGateway).findById(restaurantId);
        verify(menuRepositoryGateway, never()).findByNameAndRestaurantId(anyString(), any(Long.class));
        verify(menuRepositoryGateway, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Deve lançar DuplicatedDataException quando já existe um menu com o mesmo nome para o restaurante")
    void shouldThrowDuplicatedDataExceptionWhenMenuNameAlreadyExists() {

        Long restaurantId = 1L;
        String menuName = "Menu Test";

        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        Menu existingMenu = mock(Menu.class);
        when(menuRepositoryGateway.findByNameAndRestaurantId(menuName, restaurantId))
                .thenReturn(Optional.of(existingMenu));

        var createMenuInput = new CreateMenuInput(
                menuName,
                "Menu Description",
                restaurantId,
                List.of()
        );


        DuplicatedDataException exception = assertThrows(
                DuplicatedDataException.class,
                () -> createMenuInteractor.execute(createMenuInput)
        );

        assertEquals("Menu with name '" + menuName + "' already exists for restaurant ID: " + restaurantId,
                exception.getMessage());

        verify(restaurantGateway).findById(restaurantId);
        verify(menuRepositoryGateway).findByNameAndRestaurantId(menuName, restaurantId);
        verify(menuRepositoryGateway, never()).save(any(Menu.class));
    }
}
