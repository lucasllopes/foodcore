package com.fiap.foodcore.usecase.item;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.item.ListItemInteractor;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListItemInteractorTest {

    @Mock
    private ItemGateway itemGateway;

    private ListItemInteractor listItemInteractor;

    @BeforeEach
    void setUp() {
        listItemInteractor = new ListItemInteractor(itemGateway);
    }

    @Test
    @DisplayName("Deve retornar página com itens corretamente")
    void shouldReturnPageWithItems() {
        // Arrange
        PageRequestDomain pageRequest = new PageRequestDomain(0, 2, List.of());
        Item item1 = mock(Item.class);
        when(item1.getId()).thenReturn(1L);
        when(item1.getName()).thenReturn("Item 1");
        when(item1.getDescription()).thenReturn("Desc 1");
        when(item1.getPrice()).thenReturn(new BigDecimal("10.00"));
        when(item1.getAvailability()).thenReturn("LOCAL");
        when(item1.getPhoto()).thenReturn("foto1.jpg");

        Item item2 = mock(Item.class);
        when(item2.getId()).thenReturn(2L);
        when(item2.getName()).thenReturn("Item 2");
        when(item2.getDescription()).thenReturn("Desc 2");
        when(item2.getPrice()).thenReturn(new BigDecimal("20.00"));
        when(item2.getAvailability()).thenReturn("DELIVERY");
        when(item2.getPhoto()).thenReturn("foto2.jpg");

        DomainPage<Item> domainPage = new DomainPage<>(List.of(item1, item2), 0, 2, 2L);
        when(itemGateway.findAll(pageRequest)).thenReturn(domainPage);

        // Act
        DomainPage<ItemCreateOutput> result = listItemInteractor.execute(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size());
        assertEquals(0, result.getPage());
        assertEquals(2, result.getSize());
        assertEquals(2L, result.getTotalElements());

        ItemCreateOutput output1 = result.getItems().get(0);
        assertEquals(1L, output1.id());
        assertEquals("Item 1", output1.name());
        assertEquals("Desc 1", output1.description());
        assertEquals(new BigDecimal("10.00"), output1.price());
        assertEquals("LOCAL", output1.availability());
        assertEquals("foto1.jpg", output1.photo());

        ItemCreateOutput output2 = result.getItems().get(1);
        assertEquals(2L, output2.id());
        assertEquals("Item 2", output2.name());
        assertEquals("Desc 2", output2.description());
        assertEquals(new BigDecimal("20.00"), output2.price());
        assertEquals("DELIVERY", output2.availability());
        assertEquals("foto2.jpg", output2.photo());

        verify(itemGateway).findAll(pageRequest);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando não houver itens")
    void shouldReturnEmptyPage() {
        // Arrange
        PageRequestDomain pageRequest = new PageRequestDomain(0, 2, List.of());
        DomainPage<Item> emptyPage = new DomainPage<>(List.of(), 0, 2, 0L);
        when(itemGateway.findAll(pageRequest)).thenReturn(emptyPage);

        // Act
        DomainPage<ItemCreateOutput> result = listItemInteractor.execute(pageRequest);

        // Assert
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotalElements());
        verify(itemGateway).findAll(pageRequest);
    }
}
