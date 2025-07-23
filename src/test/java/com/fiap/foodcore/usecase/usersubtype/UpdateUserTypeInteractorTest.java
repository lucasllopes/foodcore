package com.fiap.foodcore.usecase.usersubtype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.input.UpdateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.interactor.usersubtype.UpdateUserSubtypeInteractor;
import com.fiap.foodcore.application.usecase.output.UpdateUserSubtypeOutput;
import com.fiap.foodcore.domain.UserSubtype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserTypeInteractorTest {

    @Mock
    private UserSubtypeGateway userSubtypeGateway;

    @InjectMocks
    private UpdateUserSubtypeInteractor updateUserTypeInteractor;

    @Test
    void shouldUpdateUserTypeSuccessfully() {
        // Arrange
        Long id = 1L;
        UpdateUserSubtypeInput input = new UpdateUserSubtypeInput("OWNER");

        UserSubtype existingUserType = UserSubtype.reconstruct(id, "OLD NAME", null);
        UserSubtype updatedUserType = UserSubtype.reconstruct(id, "OWNER", null);

        when(userSubtypeGateway.findById(id)).thenReturn(Optional.of(existingUserType));
        when(userSubtypeGateway.findByNameIgnoreCase("OWNER")).thenReturn(List.of());
        when(userSubtypeGateway.save(existingUserType)).thenReturn(updatedUserType);

        // Act
        UpdateUserSubtypeOutput output = updateUserTypeInteractor.execute(id, input);

        // Assert
        assertNotNull(output);
        assertEquals("OWNER", output.name());
        verify(userSubtypeGateway).findById(id);
        verify(userSubtypeGateway).findByNameIgnoreCase("OWNER");
        verify(userSubtypeGateway).save(existingUserType);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenUserTypeNotFoundById() {
        // Arrange
        Long id = 1L;
        UpdateUserSubtypeInput input = new UpdateUserSubtypeInput("OWNER");

        when(userSubtypeGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> {
            updateUserTypeInteractor.execute(id, input);
        });

        verify(userSubtypeGateway).findById(id);
        verify(userSubtypeGateway, never()).findByNameIgnoreCase(anyString());
        verify(userSubtypeGateway, never()).save(any());
    }

    @Test
    void shouldThrowDuplicatedDataExceptionWhenUserTypeNameAlreadyExists() {
        // Arrange
        Long id = 1L;
        UpdateUserSubtypeInput input = new UpdateUserSubtypeInput("OWNER");

        UserSubtype existingUserType = UserSubtype.reconstruct(id, "OLD_NAME", null);
        UserSubtype duplicatedUserType = UserSubtype.reconstruct(2L, "OWNER", null);

        when(userSubtypeGateway.findById(id)).thenReturn(Optional.of(existingUserType));
        when(userSubtypeGateway.findByNameIgnoreCase("OWNER")).thenReturn(List.of(duplicatedUserType));


        // Act & Assert
        assertThrows(DuplicatedDataException.class, () -> {
            updateUserTypeInteractor.execute(id, input);
        });

        verify(userSubtypeGateway).findById(id);
        verify(userSubtypeGateway).findByNameIgnoreCase("OWNER");
        verify(userSubtypeGateway, never()).save(any());
    }
}
