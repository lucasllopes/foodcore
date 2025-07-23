package com.fiap.foodcore.usecase.usersubtype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.interactor.usersubtype.FindUserSubtypeByIdInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.domain.UserSubtype;
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
    private UserSubtypeGateway userSubtypeGateway;

    @InjectMocks
    private FindUserSubtypeByIdInteractor interactor;

    @Test
    void shouldReturnUserTypeSuccessfully() {
        // Arrange
        Long id = 1L;
        UserSubtype domain = UserSubtype.reconstruct(id, "OWNER", null);
        CreateUserSubtypeOutput expectedOutput = new CreateUserSubtypeOutput(id,"OWNER");

        when(userSubtypeGateway.findById(id)).thenReturn(Optional.of(domain));

        // Act
        CreateUserSubtypeOutput result = interactor.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals(expectedOutput.name(), result.name());
        verify(userSubtypeGateway).findById(id);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenIdDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(userSubtypeGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> interactor.execute(id));
        verify(userSubtypeGateway).findById(id);
    }
}
