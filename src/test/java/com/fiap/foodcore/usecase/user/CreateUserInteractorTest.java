package com.fiap.foodcore.usecase.user;

import com.fiap.foodcore.application.strategy.CreateUserStrategy;
import com.fiap.foodcore.application.strategy.CreateUserStrategyFactory;
import com.fiap.foodcore.application.usecase.interactor.user.CreateUserInteractor;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.UserTypeDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserInteractorTest {

    @Mock
    private CreateUserStrategyFactory strategyFactory;

    @Mock
    private CreateUserStrategy donoStrategy;

    @Mock
    private CreateUserStrategy clienteStrategy;

    @InjectMocks
    private CreateUserInteractor interactor;

    @Test
    void deveCriarUsuarioDonoComSucesso() {
        // Arrange
        CreateUserInput input = new CreateUserInput("User Owner", "userowner@email.com", "userowner", "123", UserTypeDomain.DONO, List.of());
        CreateUserOutput expectedOutput = new CreateUserOutput(1L, "User Owner", "userowner@email.com", "userowner", UserTypeDomain.DONO, List.of());

        when(strategyFactory.getStrategy(UserTypeDomain.DONO)).thenReturn(donoStrategy);
        when(donoStrategy.create(input)).thenReturn(expectedOutput);

        // Act
        CreateUserOutput output = interactor.execute(input);

        // Assert
        assertEquals(expectedOutput.id(), output.id());
        assertEquals(expectedOutput.nome(), output.nome());
        assertEquals(expectedOutput.email(), output.email());
        assertEquals(expectedOutput.login(), output.login());
        assertEquals(expectedOutput.tipo(), output.tipo());
        verify(strategyFactory, times(1)).getStrategy(UserTypeDomain.DONO);
        verify(donoStrategy, times(1)).create(input);
        verify(strategyFactory, never()).getStrategy(UserTypeDomain.CLIENTE);
    }

    @Test
    void deveCriarUsuarioClienteComSucesso() {
        // Arrange
        CreateUserInput input = new CreateUserInput("User Customer", "usercustomer@email.com", "usercustomer", "123", UserTypeDomain.CLIENTE, List.of());
        CreateUserOutput expectedOutput = new CreateUserOutput(1L, "User Customer", "user@email.com", "usercustomer", UserTypeDomain.CLIENTE, List.of());

        when(strategyFactory.getStrategy(UserTypeDomain.CLIENTE)).thenReturn(clienteStrategy);
        when(clienteStrategy.create(input)).thenReturn(expectedOutput);

        // Act
        CreateUserOutput output = interactor.execute(input);

        // Assert
        assertEquals(expectedOutput.id(), output.id());
        assertEquals(expectedOutput.nome(), output.nome());
        assertEquals(expectedOutput.email(), output.email());
        assertEquals(expectedOutput.login(), output.login());
        assertEquals(expectedOutput.tipo(), output.tipo());

        verify(strategyFactory, times(1)).getStrategy(UserTypeDomain.CLIENTE);
        verify(clienteStrategy, times(1)).create(input);
        verify(strategyFactory, never()).getStrategy(UserTypeDomain.DONO);
    }
}
