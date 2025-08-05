package com.fiap.foodcore.usecase.usersubtype;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.interactor.usersubtype.DeleteUserSubtypeInteractor;
import com.fiap.foodcore.domain.UserSubtype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DeleteUserSubtypeInteractorTest {

    @Mock
    private UserSubtypeGateway userSubtypeGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private DeleteUserSubtypeInteractor deleteUserSubtypeInteractor;

    @Test
    void shouldDeleteUserSubtypeSuccessfully() {
        // Arrange
        var userSubtype = UserSubtype.reconstruct(1L, "DELETE", LocalDateTime.now());

        when(userSubtypeGateway.findById(1L)).thenReturn(Optional.of(userSubtype));
        when(userGateway.existsByUserSubType(1L)).thenReturn(false);
        // Act
        deleteUserSubtypeInteractor.execute(1L);

        // Assert
        verify(userSubtypeGateway, times(1)).findById(1L);
        verify(userSubtypeGateway, times(1)).delete(userSubtype);
    }
}
