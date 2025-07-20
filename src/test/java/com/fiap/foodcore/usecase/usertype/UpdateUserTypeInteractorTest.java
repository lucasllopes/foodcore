package com.fiap.foodcore.usecase.usertype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.input.UpdateUserTypeInput;
import com.fiap.foodcore.application.usecase.interactor.usertype.UpdateUserTypeInteractor;
import com.fiap.foodcore.application.usecase.output.UpdateUserTypeOutput;
import com.fiap.foodcore.domain.UserType;
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
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private UpdateUserTypeInteractor updateUserTypeInteractor;

    @Test
    void shouldUpdateUserTypeSuccessfully() {
        // Arrange
        Long id = 1L;
        UpdateUserTypeInput input = new UpdateUserTypeInput("OWNER");

        UserType existingUserType = UserType.reconstruct(id, "OLD NAME", null);
        UserType updatedUserType = UserType.reconstruct(id, "OWNER", null);

        when(userTypeGateway.findById(id)).thenReturn(Optional.of(existingUserType));
        when(userTypeGateway.findByNameIgnoreCase("OWNER")).thenReturn(List.of());
        when(userTypeGateway.save(existingUserType)).thenReturn(updatedUserType);

        // Act
        UpdateUserTypeOutput output = updateUserTypeInteractor.execute(id, input);

        // Assert
        assertNotNull(output);
        assertEquals("OWNER", output.name());
        verify(userTypeGateway).findById(id);
        verify(userTypeGateway).findByNameIgnoreCase("OWNER");
        verify(userTypeGateway).save(existingUserType);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenUserTypeNotFoundById() {
        // Arrange
        Long id = 1L;
        UpdateUserTypeInput input = new UpdateUserTypeInput("OWNER");

        when(userTypeGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> {
            updateUserTypeInteractor.execute(id, input);
        });

        verify(userTypeGateway).findById(id);
        verify(userTypeGateway, never()).findByNameIgnoreCase(anyString());
        verify(userTypeGateway, never()).save(any());
    }

    @Test
    void shouldThrowDuplicatedDataExceptionWhenUserTypeNameAlreadyExists() {
        // Arrange
        Long id = 1L;
        UpdateUserTypeInput input = new UpdateUserTypeInput("OWNER");

        UserType existingUserType = UserType.reconstruct(id, "OLD_NAME", null);
        UserType duplicatedUserType = UserType.reconstruct(2L, "OWNER", null);

        when(userTypeGateway.findById(id)).thenReturn(Optional.of(existingUserType));
        when(userTypeGateway.findByNameIgnoreCase("OWNER")).thenReturn(List.of(duplicatedUserType));


        // Act & Assert
        assertThrows(DuplicatedDataException.class, () -> {
            updateUserTypeInteractor.execute(id, input);
        });

        verify(userTypeGateway).findById(id);
        verify(userTypeGateway).findByNameIgnoreCase("OWNER");
        verify(userTypeGateway, never()).save(any());
    }
}
