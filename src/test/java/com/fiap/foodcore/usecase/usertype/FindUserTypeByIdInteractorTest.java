package com.fiap.foodcore.usecase.usertype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.input.UpdateUserTypeInput;
import com.fiap.foodcore.application.usecase.interactor.usertype.FindUserTypeByIdInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserTypeOutput;
import com.fiap.foodcore.domain.User;
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
public class FindUserTypeByIdInteractorTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private FindUserTypeByIdInteractor interactor;

    @Test
    void shouldReturnUserTypeSuccessfully() {
        // Arrange
        Long id = 1L;
        UserType domain = UserType.reconstruct(id, "OWNER", null);
        CreateUserTypeOutput expectedOutput = new CreateUserTypeOutput(id,"OWNER");

        when(userTypeGateway.findById(id)).thenReturn(Optional.of(domain));

        // Act
        CreateUserTypeOutput result = interactor.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOutput.name(), result.name());
        verify(userTypeGateway).findById(id);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenIdDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(userTypeGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> interactor.execute(id));
        verify(userTypeGateway).findById(id);
    }
}
