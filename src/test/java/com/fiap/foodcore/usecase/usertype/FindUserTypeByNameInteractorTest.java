package com.fiap.foodcore.usecase.usertype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.interactor.usertype.FindUserTypeByNameInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.domain.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindUserTypeByNameInteractorTest {

    @Mock
    private UserTypeGateway gateway;

    @InjectMocks
    private FindUserTypeByNameInteractor interactor;

    @Test
    void shouldReturnUserTypeSuccessfully() {
        // Arrange
        String name = "OWNER";
        Long id = 1L;
        UserType userType = UserType.reconstruct(id, name, null);
        CreateUserTypeOutput expectedOutput = new CreateUserTypeOutput(id, name);

        when(gateway.findByNameIgnoreCase(name)).thenReturn(Optional.of(userType));

        // Act
        CreateUserTypeOutput result = interactor.execute(name);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOutput.id(), result.id());
        assertEquals(expectedOutput.name(), result.name());
        verify(gateway).findByNameIgnoreCase(name);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenNameNotFound() {
        // Arrange
        String name = "USER DOES NOT EXIST";
        when(gateway.findByNameIgnoreCase(name)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> interactor.execute(name));
        verify(gateway).findByNameIgnoreCase(name);
    }
}
