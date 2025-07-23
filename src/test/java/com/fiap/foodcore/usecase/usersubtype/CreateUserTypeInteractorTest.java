package com.fiap.foodcore.usecase.usersubtype;

import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.input.CreateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.interactor.usersubtype.CreateUserSubtypeInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.domain.UserSubtype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CreateUserTypeInteractorTest {

    @Mock
    private UserSubtypeGateway userTypeGateway;

    @InjectMocks
    private CreateUserSubtypeInteractor createUserTypeInteractor;

    @Test
    void shouldCreateUserSubtypeSuccessfully() {
        // Arrange
        CreateUserSubtypeInput input = new CreateUserSubtypeInput("OWNER");
        UserSubtype domainUserSubtypeSaved = UserSubtype.create("OWNER");

        when(userTypeGateway.save(any(UserSubtype.class))).thenReturn(domainUserSubtypeSaved);

        // Act
        CreateUserSubtypeOutput output = createUserTypeInteractor.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals("OWNER", output.name());
        verify(userTypeGateway, times(1)).save(any(UserSubtype.class));
    }
}