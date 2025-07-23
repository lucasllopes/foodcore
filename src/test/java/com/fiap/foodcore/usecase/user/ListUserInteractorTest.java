package com.fiap.foodcore.usecase.user;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.interactor.user.ListUserInteractor;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ListUserInteractorTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private ListUserInteractor interactor;

    @Test
    void deveRetornarPaginaComUsuarios() {
        // Arrange
        PageRequestDomain pageRequest = new PageRequestDomain(0, 10, List.of());

        var user1 = UserTestHelper.getUserWithIdParametrized(11L);

        var user2 = UserTestHelper.getUserWithIdParametrized(25L);

        DomainPage<User> domainPage = new DomainPage<>(List.of(user1, user2), 0, 10, 2);

        when(userGateway.findAllPage(pageRequest)).thenReturn(domainPage);

        // Act
        DomainPage<CreateUserOutput> result = interactor.execute(pageRequest);

        // Assert
        assertEquals(2, result.getItems().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(11, result.getItems().get(0).id());
        assertEquals(25, result.getItems().get(1).id());

        verify(userGateway, times(1)).findAllPage(pageRequest);
    }
}
