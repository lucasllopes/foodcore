package com.fiap.foodcore.usecase.user;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.WrongPasswordException;
import com.fiap.foodcore.application.gateway.PasswordEncryptionGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.interactor.user.ChangePasswordInteractor;
import com.fiap.foodcore.application.usecase.input.ChangePasswordInput;
import com.fiap.foodcore.helper.UserTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ChangePasswordUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private PasswordEncryptionGateway passwordEncryptionGateway;

    @InjectMocks
    private ChangePasswordInteractor interactor;

    @Test
    void deveAlterarSenhaComSucesso() {
        // Arrange
        var user = UserTestHelper.getUserWithDefaultId();

        ChangePasswordInput input = new ChangePasswordInput("password", "newpassword");

        when(userGateway.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncryptionGateway.matches("password", "password")).thenReturn(true);
        when(passwordEncryptionGateway.encode("newpassword")).thenReturn("newpasswordencrypted");

        // Act
        interactor.execute(user.getId(), input);

        // Assert
        assertEquals("newpasswordencrypted", user.getSenha());
        assertNotNull(user.getDataUltimaAlteracao());
        verify(userGateway, times(1)).save(user);

    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Long id = 1L;
        ChangePasswordInput input = new ChangePasswordInput("password", "newPassword");

        when(userGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> interactor.execute(id, input));
        verify(userGateway, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualEstiverIncorreta() {
        // Arrange
        Long id = 1L;
        var user = UserTestHelper.getUserWithDefaultId();

        ChangePasswordInput input = new ChangePasswordInput("wrongPassword", "newPassword");

        when(userGateway.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncryptionGateway.matches("wrongPassword", "password")).thenReturn(false);

        // Act & Assert
        assertThrows(WrongPasswordException.class, () -> interactor.execute(id, input));
        verify(userGateway, never()).save(any());
    }
}
