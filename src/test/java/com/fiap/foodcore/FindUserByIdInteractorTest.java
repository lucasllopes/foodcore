package com.fiap.foodcore;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.FindUserByIdInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserTypeDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
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
        Long id = 1L;
        User user = User.rebuildUser(id, "User", "user@email.com", "user", "password",
                UserTypeDomain.DONO, List.of(), LocalDateTime.now());

        Mockito.when(userGateway.findById(id)).thenReturn(Optional.of(user));

        // Act
        CreateUserOutput result = interactor.execute(id);

        // Assert
        Assertions.assertEquals("User", result.nome());
        Assertions.assertEquals("user@email.com", result.email());
        Mockito.verify(userGateway, Mockito.times(1gi)).findById(id);
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
