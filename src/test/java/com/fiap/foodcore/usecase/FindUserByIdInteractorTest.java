package com.fiap.foodcore.usecase;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.interactor.FindUserByIdInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FindUserByIdInteractorTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private FindUserByIdInteractor interactor;

    @Test
    void deveRetornarUsuarioQuandoEncontrado() {
        // Arrange
        var user = UserTestHelper.getUserWithDefaultId();

        Mockito.when(userGateway.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        CreateUserOutput result = interactor.execute(user.getId());

        // Assert
        Assertions.assertEquals("User", result.nome());
        Assertions.assertEquals("user@email.com", result.email());
        Mockito.verify(userGateway, Mockito.times(1)).findById(user.getId());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Long id = 666L;
        Mockito.when(userGateway.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        Assertions.assertThrows(DataNotFoundException.class, () -> interactor.execute(id));
    }
}
