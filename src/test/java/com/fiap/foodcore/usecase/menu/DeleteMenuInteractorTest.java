package com.fiap.foodcore.usecase.menu;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.application.usecase.menu.DeleteMenuInteractor;
import com.fiap.foodcore.domain.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuInteractorTest {

    @Mock
    private MenuGateway menuGateway;
    @Mock
    private ItemGateway itemGateway; // Assuming you have an ItemGateway for the test

    private DeleteMenuInteractor deleteMenuInteractor;

    @BeforeEach
    void setUp() {
        deleteMenuInteractor = new DeleteMenuInteractor(menuGateway, itemGateway);
    }

    @Test
    @DisplayName("Deve excluir um menu com sucesso quando o menu é encontrado")
    void shouldDeleteMenuSuccessfully() {
        Long menuId = 1L;
        Menu menu = mock(Menu.class);
        when(menuGateway.findById(menuId)).thenReturn(Optional.of(menu));

        deleteMenuInteractor.execute(menuId);

        verify(menuGateway).findById(menuId);
        verify(menuGateway).delete(menu);
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando o menu não é encontrado")
    void shouldThrowRuntimeExceptionWhenMenuNotFound() {
        Long menuId = 1L;
        when(menuGateway.findById(menuId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> deleteMenuInteractor.execute(menuId)
        );

        assertEquals("Cardápio não encontrado", exception.getMessage());

        verify(menuGateway).findById(menuId);
        verify(menuGateway, never()).delete(any(Menu.class));
    }
}
