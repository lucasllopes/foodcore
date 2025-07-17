package com.fiap.foodcore.usecase;

import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.interactor.CreateUserTypeInteractor;
import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.domain.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserTypeInteractorTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private CreateUserTypeInteractor createUserTypeInteractor;

    @Test
    void shouldCreateUserTypeSuccessfully() {
        // Arrange
        CreateUserTypeInput input = new CreateUserTypeInput("OWNER");
        UserType domainUserTypeSaved = UserType.create("OWNER");

        when(userTypeGateway.save(any(UserType.class))).thenReturn(domainUserTypeSaved);

        // Act
        CreateUserTypeOutput output = createUserTypeInteractor.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals("OWNER", output.name());
        verify(userTypeGateway, times(1)).save(any(UserType.class));
    }
}