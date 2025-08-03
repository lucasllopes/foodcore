package com.fiap.foodcore.usecase.item;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.item.FindItemByIdInteractor;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindItemByIdInteractorTest {

    @Mock
    private ItemGateway itemGateway;

    private FindItemByIdInteractor findItemByIdInteractor;

    @BeforeEach
    void setUp() {
        findItemByIdInteractor = new FindItemByIdInteractor(itemGateway);
    }

    @Test
    @DisplayName("Deve retornar o item quando encontrado")
    void shouldReturnItemWhenFound() {
        // Arrange
        Long id = 1L;
        Long ownerId = 10L;
        Item item = mock(Item.class);
        when(item.getId()).thenReturn(id);
        when(item.getName()).thenReturn("Nome");
        when(item.getDescription()).thenReturn("Desc");
        when(item.getPrice()).thenReturn(new BigDecimal("10.00"));
        when(item.getAvailability()).thenReturn("LOCAL");
        when(item.getPhoto()).thenReturn("foto.jpg");
        when(item.getOwnerId()).thenReturn(ownerId); // Adicionando mock para ownerId
        when(itemGateway.findById(id)).thenReturn(Optional.of(item));

        // Act
        ItemCreateOutput output = findItemByIdInteractor.execute(id);

        // Assert
        assertNotNull(output);
        assertEquals(id, output.id());
        assertEquals("Nome", output.name());
        assertEquals("Desc", output.description());
        assertEquals(new BigDecimal("10.00"), output.price());
        assertEquals("LOCAL", output.availability());
        assertEquals("foto.jpg", output.photo());
        assertEquals(ownerId, output.ownerId()); // Verificando ownerId no resultado
        verify(itemGateway).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando item não encontrado")
    void shouldThrowExceptionWhenItemNotFound() {
        // Arrange
        Long id = 2L;
        when(itemGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        DataNotFoundException ex = assertThrows(DataNotFoundException.class, () -> findItemByIdInteractor.execute(id));
        assertEquals("Item não encontrado", ex.getMessage());
        verify(itemGateway).findById(id);
    }
}