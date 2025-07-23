package com.fiap.foodcore.usecase.usersubtype;

import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.interactor.usersubtype.FindUserSubtypeByNameInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.domain.UserSubtype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class FindUserTypeByNameInteractorTest {

    @Mock
    private UserSubtypeGateway gateway;

    @InjectMocks
    private FindUserSubtypeByNameInteractor interactor;

    @Test
    void shouldReturnUserTypeSuccessfully() {
        // Arrange
        String name = "OWNER";
        Long id = 1L;
        UserSubtype userType = UserSubtype.reconstruct(id, name, null);
        CreateUserSubtypeOutput expectedOutput = new CreateUserSubtypeOutput(id, name);

        when(gateway.findByNameIgnoreCase(name)).thenReturn(List.of(userType));

        // Act
        List<CreateUserSubtypeOutput> result = interactor.execute(name);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedOutput.id(), result.getFirst().id());
        assertEquals(expectedOutput.name(), result.getFirst().name());
        verify(gateway).findByNameIgnoreCase(name);
    }

    @Test
    void shouldReturnEmptyListWhenUserTypeNotFound() {
        // Arrange
        String name = "USER TYPE DOES NOT EXIST";
        when(gateway.findByNameIgnoreCase(name)).thenReturn(List.of());

        // Act
        List<CreateUserSubtypeOutput> result = interactor.execute(name);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(gateway).findByNameIgnoreCase(name);
    }
}
