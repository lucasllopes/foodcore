package com.fiap.foodcore.usecase;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.interactor.DeleteUserInteractor;
import com.fiap.foodcore.helper.UserTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserInteractorTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private DeleteUserInteractor interactor;

    @Test
    void deveDeletarUsuarioQuandoEncontrado() {

        // Arrange
        var user = UserTestHelper.getUserWithDefaultId();
        when(userGateway.findById(1L)).thenReturn(Optional.of(user));

        // Act
        interactor.execute(1L);

        // Assert
        verify(userGateway, times(1)).findById(1L);
        verify(userGateway, times(1)).delete(user);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        // Arrange
        when(userGateway.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> interactor.execute(1L));

        verify(userGateway, times(1)).findById(1L);
        verify(userGateway, never()).delete(any());
    }
}
