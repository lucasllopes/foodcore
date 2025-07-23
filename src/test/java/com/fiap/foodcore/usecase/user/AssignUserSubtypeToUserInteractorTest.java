package com.fiap.foodcore.usecase.user;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.input.AssignUserSubtypeToUserInput;
import com.fiap.foodcore.application.usecase.interactor.user.AssignUserTypeToUserInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserSubtype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.fiap.foodcore.domain.UserTypeDomain.COLABORADOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignUserSubtypeToUserInteractorTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserSubtypeGateway userSubtypeGateway;

    @InjectMocks
    private AssignUserTypeToUserInteractor interactor;

    @Test
    void shouldAssignUserTypeSuccessfully() {
        // Arrange
        Long userId = 1L;
        Long userTypeId = 3L;
        AssignUserSubtypeToUserInput input = new AssignUserSubtypeToUserInput(userTypeId);

        User existingUser = User.rebuildUser(userId, "User", "user@email.com", "login", "123", COLABORADOR,
                List.of(), LocalDateTime.now());

        UserSubtype userType = UserSubtype.reconstruct(userTypeId, "CUSTOMER", LocalDateTime.now());

        User updatedUser = existingUser.assignUserType(userType);

        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userSubtypeGateway.findById(userTypeId)).thenReturn(Optional.of(userType));
        when(userGateway.save(any(User.class))).thenReturn(updatedUser);

        // Act
        CreateUserOutput output = interactor.execute(userId, input);

        // Assert
        assertNotNull(output);
        verify(userGateway).findById(userId);
        verify(userSubtypeGateway).findById(userTypeId);
        verify(userGateway).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;
        AssignUserSubtypeToUserInput input = new AssignUserSubtypeToUserInput(2L);

        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> interactor.execute(userId, input));

        verify(userGateway).findById(userId);
        verifyNoInteractions(userSubtypeGateway);
    }

    @Test
    void shouldThrowExceptionWhenUserTypeNotFound() {
        Long userId = 1L;
        Long userTypeId = 666L;
        AssignUserSubtypeToUserInput input = new AssignUserSubtypeToUserInput(userTypeId);

        User existingUser = User.rebuildUser(userId, "User", "user@email.com", "login", "123", COLABORADOR,
                java.util.Collections.emptyList(), LocalDateTime.now());

        when(userGateway.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userSubtypeGateway.findById(userTypeId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> interactor.execute(userId, input));

        verify(userGateway).findById(userId);
        verify(userSubtypeGateway).findById(userTypeId);
        verify(userGateway, never()).save(any());
    }
}
