package com.fiap.foodcore.usecase.menu;

import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.usecase.menu.UpdateMenuInteractor;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.web.controller.dto.ItemUpdateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuUpdateRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMenuInteractorTest {

    @Mock
    private MenuGateway menuGateway;
    @Mock
    private RestaurantGateway restaurantGateway;

    private UpdateMenuInteractor updateMenuInteractor;


    @BeforeEach
    void setUp() {
        updateMenuInteractor = new UpdateMenuInteractor(menuGateway, restaurantGateway);
    }

    @Test
    @DisplayName("Deve atualizar um menu com sucesso quando todos os dados são válidos")
    void shouldUpdateMenuSuccessfully() {
        // Arrange
        Long menuId = 1L;
        Long restaurantId = 2L;

        String updatedMenuName = "Menu Atualizado";
        String updatedDescription = "Descrição Atualizada";

        // Configurar restaurant mock - sem configuração de getId()
        Restaurant restaurant = mock(Restaurant.class);

        // Configurar menu original mock
        Menu originalMenu = mock(Menu.class);
        lenient().when(originalMenu.getId()).thenReturn(menuId);

        // Configurar menu atualizado mock
        Menu updatedMenu = mock(Menu.class);
        when(updatedMenu.getId()).thenReturn(menuId);
        when(updatedMenu.getName()).thenReturn(updatedMenuName);
        when(updatedMenu.getDescription()).thenReturn(updatedDescription);
        when(updatedMenu.getRestaurantId()).thenReturn(restaurant);

        // Configurar diretamente o restaurantId no mock
        Restaurant savedRestaurant = mock(Restaurant.class);
        when(savedRestaurant.getId()).thenReturn(restaurantId);
        when(updatedMenu.getRestaurantId()).thenReturn(savedRestaurant);

        when(updatedMenu.getItems()).thenReturn(Collections.emptyList());

        // Configurar comportamento do gateway
        when(menuGateway.findById(menuId)).thenReturn(Optional.of(originalMenu));
        when(menuGateway.findByName(updatedMenuName)).thenReturn(Optional.empty());
        when(originalMenu.atualizarInformacoes(eq(updatedMenuName), eq(updatedDescription), eq(restaurantId), any())).thenReturn(updatedMenu);
        when(menuGateway.save(updatedMenu)).thenReturn(updatedMenu);

        // Configurar comportamento do RestaurantGateway
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(savedRestaurant));

        // Configurar DTO
        var itemUpdateDTO = new ItemUpdateRequestDTO(
                1L,
                "Item Atualizado",
                "Descrição do Item Atualizado",
                BigDecimal.valueOf(15.0),
                "LOCAL",
                "photo-url-updated"
        );

        var menuUpdateDTO = new MenuUpdateRequestDTO(
                updatedMenuName,
                updatedDescription,
                restaurantId,
                List.of(itemUpdateDTO)
        );

        // Act
        var result = updateMenuInteractor.execute(menuId, menuUpdateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(menuId, result.id());
        assertEquals(updatedMenuName, result.name());
        assertEquals(updatedDescription, result.description());
        assertEquals(restaurantId, result.restaurantId());

        // Verify
        verify(menuGateway).findById(menuId);
        verify(menuGateway).findByName(updatedMenuName);
        verify(originalMenu).atualizarInformacoes(eq(updatedMenuName), eq(updatedDescription), eq(restaurantId), any());
        verify(menuGateway).save(updatedMenu);
    }

    @Test
    @DisplayName("Deve lançar DuplicatedDataException quando já existe outro menu com o mesmo nome")
    void shouldThrowDuplicatedDataExceptionWhenNameIsDuplicated() {
        // Arrange
        Long menuId = 1L;
        Long otherMenuId = 2L;
        Long restaurantId = 3L;
        String updatedName = "Menu Duplicado";

        // Menu original
        Menu originalMenu = mock(Menu.class);
        lenient().when(originalMenu.getId()).thenReturn(menuId);

        // Menu duplicado (outro menu com o mesmo nome)
        Menu duplicateMenu = mock(Menu.class);

        when(menuGateway.findById(menuId)).thenReturn(Optional.of(originalMenu));
        when(menuGateway.findByName(updatedName)).thenReturn(Optional.of(duplicateMenu));

        var menuUpdateDTO = new MenuUpdateRequestDTO(
                updatedName,
                "Descrição Atualizada",
                restaurantId,
                Collections.<ItemUpdateRequestDTO>emptyList()
        );

        // Act & Assert
        DuplicatedDataException exception = assertThrows(
                DuplicatedDataException.class,
                () -> updateMenuInteractor.execute(menuId, menuUpdateDTO)
        );

        assertEquals("Nome de Cardápio já utilizado", exception.getMessage());

        // Verify
        verify(menuGateway).findById(menuId);
        verify(menuGateway).findByName(updatedName);
        verify(menuGateway, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o menu tem o mesmo nome mas é o mesmo ID")
    void shouldNotThrowExceptionWhenMenuHasSameNameButSameId() {
        // Arrange
        Long menuId = 1L;
        Long restaurantId = 2L;
        String menuName = "Menu Test";
        String updatedDescription = "Descrição Atualizada";

        // Configurar restaurant mock
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getId()).thenReturn(restaurantId);

        // Menu original e atualizado
        Menu originalMenu = mock(Menu.class);
        when(originalMenu.getId()).thenReturn(menuId);
        // Usar lenient() para configurações não utilizadas diretamente
        lenient().when(originalMenu.getName()).thenReturn(menuName);
        lenient().when(originalMenu.getRestaurantId()).thenReturn(restaurant);
        lenient().when(originalMenu.getItems()).thenReturn(List.of());

        Menu updatedMenu = mock(Menu.class);
        when(updatedMenu.getId()).thenReturn(menuId);
        when(updatedMenu.getName()).thenReturn(menuName);
        when(updatedMenu.getDescription()).thenReturn(updatedDescription);
        when(updatedMenu.getRestaurantId()).thenReturn(restaurant);
        when(updatedMenu.getItems()).thenReturn(List.of());

        when(menuGateway.findById(menuId)).thenReturn(Optional.of(originalMenu));
        when(menuGateway.findByName(menuName)).thenReturn(Optional.of(originalMenu));
        when(originalMenu.atualizarInformacoes(eq(menuName), eq(updatedDescription), eq(restaurantId), any())).thenReturn(updatedMenu);
        when(menuGateway.save(updatedMenu)).thenReturn(updatedMenu);

        // Configurar comportamento do RestaurantGateway
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        var menuUpdateDTO = new MenuUpdateRequestDTO(
                menuName,
                updatedDescription,
                restaurantId,
                Collections.<ItemUpdateRequestDTO>emptyList()
        );

        // Act
        var result = updateMenuInteractor.execute(menuId, menuUpdateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(menuId, result.id());
        assertEquals(menuName, result.name());

        // Verify
        verify(menuGateway).findById(menuId);
        verify(menuGateway).findByName(menuName);
        verify(originalMenu).atualizarInformacoes(eq(menuName), eq(updatedDescription), eq(restaurantId), any());
        verify(menuGateway).save(updatedMenu);
    }
}
