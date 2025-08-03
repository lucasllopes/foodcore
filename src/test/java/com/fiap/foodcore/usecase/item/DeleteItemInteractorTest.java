package com.fiap.foodcore.usecase.item;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.item.DeleteItemInteractor;
import com.fiap.foodcore.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteItemInteractorTest {

    @Mock
    private ItemGateway itemGateway;

    private DeleteItemInteractor deleteItemInteractor;

    @BeforeEach
    void setUp() {
        deleteItemInteractor = new DeleteItemInteractor(itemGateway);
    }

    @Test
    @DisplayName("Deve deletar o item com sucesso quando encontrado")
    void shouldDeleteItemSuccessfully() {
        // Arrange
        Long itemId = 1L;
        Item item = mock(Item.class);
        when(itemGateway.findById(itemId)).thenReturn(Optional.of(item));

        // Act
        deleteItemInteractor.execute(itemId);

        // Assert
        verify(itemGateway).findById(itemId);
        verify(itemGateway).delete(item.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item não for encontrado")
    void shouldThrowExceptionWhenItemNotFound() {
        // Arrange
        Long itemId = 2L;
        when(itemGateway.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteItemInteractor.execute(itemId));
        assertEquals("Item não encontrado", exception.getMessage());
        verify(itemGateway).findById(itemId);
        verify(itemGateway, never()).delete(any());
    }
}
