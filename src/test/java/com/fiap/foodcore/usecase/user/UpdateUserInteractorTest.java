package com.fiap.foodcore.usecase.user;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.interactor.user.UpdateUserInteractor;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.helper.UserTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateUserInteractorTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UpdateUserInteractor interactor;

    @Test
    void deveAtualizarUsuarioQuandoEncontrado() {

        // Arrange
        var user = UserTestHelper.getUserWithDefaultId();
        UpdateUserInput input = new UpdateUserInput("User update", "userupdate@email.com", List.of());

        Mockito.when(userGateway.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userGateway.findByEmail("userupdate@email.com")).thenReturn(Optional.empty());
        Mockito.when(userGateway.save(user)).thenReturn((user));

        // Act
        CreateUserOutput output = interactor.execute(user.getId(), input);

        // Assert
        Assertions.assertEquals(input.nome(), output.nome());
        Assertions.assertEquals(input.email(), output.email());

        Mockito.verify(userGateway, Mockito.times(1)).findById(1L);
        Mockito.verify(userGateway, Mockito.times(1)).findByEmail(input.email());
        Mockito.verify(userGateway, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {

        // Arrange
        UpdateUserInput input = new UpdateUserInput("User update", "userupdate@email.com", List.of());
        Mockito.when(userGateway.findById(666L)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(DataNotFoundException.class, () -> interactor.execute(666L, input));
        Mockito.verify(userGateway, Mockito.times(1)).findById(666L);
        Mockito.verify(userGateway, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deveLancarExcecaoQuandoEmailForDeOutroUsuario() {

        // Arrange
        UpdateUserInput input = new UpdateUserInput("User update", "userupdate@email.com", List.of());
        User existingUser = UserTestHelper.getUserWithIdParametrized(1L);
        User outroUserMesmoEmail = UserTestHelper.getUserWithIdParametrized(555L);

        Mockito.when(userGateway.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userGateway.findByEmail(input.email())).thenReturn(Optional.of(outroUserMesmoEmail));

        // Act & Assert
        Assertions.assertThrows(DuplicatedDataException.class, () -> interactor.execute(1L, input));

        Mockito.verify(userGateway, Mockito.times(1)).findById(1L);
        Mockito.verify(userGateway, Mockito.times(1)).findByEmail(input.email());
        Mockito.verify(userGateway, Mockito.never()).save(Mockito.any());
    }
}
