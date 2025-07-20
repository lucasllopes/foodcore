package com.fiap.foodcore.usecase.user;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.input.AssignUserTypeToUserInput;
import com.fiap.foodcore.application.usecase.interactor.user.AssignUserTypeToUserInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignUserTypeToUserInteractorTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private AssignUserTypeToUserInteractor interactor;

    @Test
    void shouldAssignUserTypeSuccessfully() {
        // Arrange
        Long userId = 1L;
        Long userTypeId = 3L;
        AssignUserTypeToUserInput input = new AssignUserTypeToUserInput(userTypeId);

        User existingUser = User.rebuildUser(userId, "User", "user@email.com", "login", "123", null,
                List.of(), LocalDateTime.now());

        UserType userType = UserType.reconstruct(userTypeId, "CUSTOMER", LocalDateTime.now());

        User updatedUser = existingUser.assignUserType(userType);

        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.of(userType));
        when(userGateway.save(any(User.class))).thenReturn(updatedUser);

        // Act
        CreateUserOutput output = interactor.execute(userId, input);

        // Assert
        assertNotNull(output);
        verify(userGateway).findById(userId);
        verify(userTypeGateway).findById(userTypeId);
        verify(userGateway).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;
        AssignUserTypeToUserInput input = new AssignUserTypeToUserInput(2L);

        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> interactor.execute(userId, input));

        verify(userGateway).findById(userId);
        verifyNoInteractions(userTypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeNotFound() {
        Long userId = 1L;
        Long userTypeId = 666L;
        AssignUserTypeToUserInput input = new AssignUserTypeToUserInput(userTypeId);

        User existingUser = User.rebuildUser(userId, "User", "user@email.com", "login", "123", null,
                java.util.Collections.emptyList(), LocalDateTime.now());

        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> interactor.execute(userId, input));

        verify(userGateway).findById(userId);
        verify(userTypeGateway).findById(userTypeId);
        verify(userGateway, never()).save(any());
    }
}
