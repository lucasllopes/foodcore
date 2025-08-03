package com.fiap.foodcore.usecase.item;

import com.fiap.foodcore.application.exception.BusinessException;
import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.usecase.item.DeleteItemInteractor;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteItemInteractorTest {

    @Mock
    private ItemGateway itemGateway;

    @Mock
    private MenuGateway menuGateway;

    @Mock
    private Item mockItem;

    private DeleteItemInteractor deleteItemInteractor;

    @BeforeEach
    void setUp() {
        deleteItemInteractor = new DeleteItemInteractor(itemGateway, menuGateway);
        // Removido as configurações desnecessárias do setUp
    }

    @Test
    @DisplayName("Deve deletar o item com sucesso quando encontrado e não vinculado a menus")
    void shouldDeleteItemSuccessfully() {
        // Arrange
        Long itemId = 1L;
        // Apenas configuração necessária
        when(mockItem.getId()).thenReturn(itemId);
        // A linha abaixo não é utilizada e deve ser removida
        // when(mockItem.getOwnerId()).thenReturn(10L);

        when(itemGateway.findById(itemId)).thenReturn(Optional.of(mockItem));
        when(menuGateway.findMenusByItemId(itemId)).thenReturn(Collections.emptyList());

        // Act
        deleteItemInteractor.execute(itemId);

        // Assert
        verify(itemGateway).findById(itemId);
        verify(menuGateway).findMenusByItemId(itemId);
        verify(itemGateway).delete(mockItem.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item não for encontrado")
    void shouldThrowExceptionWhenItemNotFound() {
        // Arrange
        Long itemId = 2L;
        when(itemGateway.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> deleteItemInteractor.execute(itemId));
        assertEquals("Item não encontrado", exception.getMessage());
        verify(itemGateway).findById(itemId);
        verify(itemGateway, never()).delete(any());
        verify(menuGateway, never()).findMenusByItemId(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item estiver vinculado a menus")
    void shouldThrowExceptionWhenItemIsLinkedToMenus() {
        // Arrange
        Long itemId = 1L;
        // Removemos as configurações desnecessárias do mockItem

        when(itemGateway.findById(itemId)).thenReturn(Optional.of(mockItem));

        // Criar mocks de menus vinculados
        Menu menu1 = mock(Menu.class);
        when(menu1.getName()).thenReturn("Menu 1");

        Menu menu2 = mock(Menu.class);
        when(menu2.getName()).thenReturn("Menu 2");

        when(menuGateway.findMenusByItemId(itemId)).thenReturn(List.of(menu1, menu2));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> deleteItemInteractor.execute(itemId));

        // Verificar que a mensagem contém os nomes dos menus
        assertTrue(exception.getMessage().contains("Menu 1"));
        assertTrue(exception.getMessage().contains("Menu 2"));

        verify(itemGateway).findById(itemId);
        verify(menuGateway).findMenusByItemId(itemId);
        verify(itemGateway, never()).delete(any());
    }
}