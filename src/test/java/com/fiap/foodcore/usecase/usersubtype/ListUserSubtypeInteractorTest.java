package com.fiap.foodcore.usecase.usersubtype;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.interactor.user.ListUserInteractor;
import com.fiap.foodcore.application.usecase.interactor.usersubtype.ListUserSubtypeInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.helper.UserTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ListUserSubtypeInteractorTest {


    @Mock
    private UserSubtypeGateway userSubtypeGateway;

    @InjectMocks
    private ListUserSubtypeInteractor interactor;

    @Test
    void deveRetornarPaginaComUsuarios() {
        // Arrange
        PageRequestDomain pageRequest = new PageRequestDomain(0, 10, List.of());

        // Arrange
        Long id = 1L;
        UserSubtype domain = UserSubtype.reconstruct(id, "OWNER", null);
        CreateUserSubtypeOutput expectedOutput = new CreateUserSubtypeOutput(id,"OWNER");


        DomainPage<UserSubtype> domainPage = new DomainPage<>(List.of(domain), 0, 10, 1);

        when(userSubtypeGateway.findAllPage(pageRequest)).thenReturn(domainPage);

        // Act
        DomainPage<CreateUserSubtypeOutput> result = interactor.execute(pageRequest);

        // Assert
        assertEquals(1, result.getItems().size());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getItems().get(0).id());

        verify(userSubtypeGateway, times(1)).findAllPage(pageRequest);
    }
}
