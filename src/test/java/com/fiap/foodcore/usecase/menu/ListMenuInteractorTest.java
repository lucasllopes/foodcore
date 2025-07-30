package com.fiap.foodcore.usecase.menu;

import com.fiap.foodcore.application.usecase.menu.ListMenuInteractor;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.MenuRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMenuInteractorTest {

    @Mock
    private MenuRepositoryGateway menuRepositoryGateway;

    private ListMenuInteractor listMenuInteractor;

    @BeforeEach
    void setUp() {
        listMenuInteractor = new ListMenuInteractor(menuRepositoryGateway);
    }

    @Test
    @DisplayName("Deve listar menus com sucesso quando existem menus")
    void shouldListMenusSuccessfully() {
        // Arrange
        PageRequestDomain pageRequest = mock(PageRequestDomain.class);
        MenuCreateOutput menuOutput = mock(MenuCreateOutput.class);

        // Cria objetos reais em vez de mocks para evitar problemas com tipos genéricos
        List<MenuCreateOutput> outputList = List.of(menuOutput);
        DomainPage<MenuCreateOutput> outputPage = new DomainPage<>(outputList, 0, 10, 1);

        DomainPage<Menu> domainPage = mock(DomainPage.class);

        // Usa doReturn().when() em vez de when().thenReturn() para evitar problemas de tipo genérico
        doReturn(domainPage).when(menuRepositoryGateway).findAll(pageRequest);
        doReturn(outputPage).when(domainPage).map(any());

        // Act
        DomainPage<MenuCreateOutput> result = listMenuInteractor.execute(pageRequest);

        // Assert
        assertNotNull(result);
        assertFalse(result.getItems().isEmpty());

        verify(menuRepositoryGateway).findAll(pageRequest);
        verify(domainPage).map(any());
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não existem menus")
    void shouldReturnEmptyPageWhenNoMenusExist() {
        // Arrange
        PageRequestDomain pageRequest = mock(PageRequestDomain.class);

        // Usa objetos reais para o resultado
        DomainPage<MenuCreateOutput> emptyOutputPage = new DomainPage<>(
                Collections.emptyList(), 0, 10, 0);

        DomainPage<Menu> emptyDomainPage = mock(DomainPage.class);

        // Usa doReturn().when() em vez de when().thenReturn()
        doReturn(emptyDomainPage).when(menuRepositoryGateway).findAll(pageRequest);
        doReturn(emptyOutputPage).when(emptyDomainPage).map(any());

        // Act
        DomainPage<MenuCreateOutput> result = listMenuInteractor.execute(pageRequest);

        // Assert
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(menuRepositoryGateway).findAll(pageRequest);
        verify(emptyDomainPage).map(any());
    }
}