package com.fiap.foodcore.usecase.item;

import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.item.CreateItemInteractor;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.infrastructure.gateways.ItemRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateItemInteractorTest {

    @Mock
    private ItemRepositoryGateway itemRepositoryGateway;

    private CreateItemInteractor createItemInteractor;

    @BeforeEach
    void setUp() {
        createItemInteractor = new CreateItemInteractor(itemRepositoryGateway);
    }

    @Test
    @DisplayName("Deve criar um item com sucesso")
    void shouldCreateItemSuccessfully() {
        // Arrange
        String name = "Item de Teste";
        String description = "Descrição do Item";
        BigDecimal price = new BigDecimal("25.50");
        String availability = "LOCAL";
        String photo = "url_da_foto.jpg";
        Long itemId = 1L;

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .build();

        // Simular o ID sendo definido após o save
        setItemId(item, itemId);

        //when(itemRepositoryGateway.save(any(Item.class))).thenReturn(item);
        when(itemRepositoryGateway.save(any(Item.class))).thenAnswer(invocation -> {
            Item savedItem = invocation.getArgument(0);
            setItemId(savedItem, itemId);
            return savedItem;
        });

        // Act
        var result = createItemInteractor.execute(createItemInput);

        // Assert
        assertNotNull(result);
        assertEquals(itemId, result.id());
        assertEquals(name, result.name());
        assertEquals(description, result.description());
        assertEquals(price, result.price());
        assertEquals(availability, result.availability());
        assertEquals(photo, result.photo());

        // Verify
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryGateway).save(itemCaptor.capture());

        Item savedItem = itemCaptor.getValue();
        assertEquals(name, savedItem.getName());
        assertEquals(description, savedItem.getDescription());
        assertEquals(price, savedItem.getPrice());
        assertEquals(availability, savedItem.getAvailability());
        assertEquals(photo, savedItem.getPhoto());
    }

    @Test
    @DisplayName("Deve criar um item sem foto")
    void shouldCreateItemWithoutPhoto() {
        // Arrange
        String name = "Item sem Foto";
        String description = "Descrição do Item";
        BigDecimal price = new BigDecimal("15.90");
        String availability = "LOCAL";
        String photo = null;
        Long itemId = 2L;

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .build();

        // Simular o ID sendo definido após o save
        setItemId(item, itemId);

        when(itemRepositoryGateway.save(any(Item.class))).thenAnswer(invocation -> {
            Item savedItem = invocation.getArgument(0);
            setItemId(savedItem, itemId);
            return savedItem;
        });

        // Act
        var result = createItemInteractor.execute(createItemInput);

        // Assert
        assertNotNull(result);
        assertEquals(itemId, result.id());
        assertEquals(name, result.name());
        assertEquals(description, result.description());
        assertEquals(price, result.price());
        assertEquals(availability, result.availability());
        assertEquals(null, result.photo());

        verify(itemRepositoryGateway).save(any(Item.class));
    }

    @Test
    @DisplayName("Deve criar um item com availability DELIVERY")
    void shouldCreateItemWithDeliveryAvailability() {
        // Arrange
        String name = "Item de Delivery";
        String description = "Descrição do Item de Delivery";
        BigDecimal price = new BigDecimal("30.00");
        String availability = "DELIVERY";
        String photo = "url_foto_delivery.jpg";
        Long itemId = 3L;

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .build();

        // Simular o ID sendo definido após o save
        setItemId(item, itemId);

        when(itemRepositoryGateway.save(any(Item.class))).thenAnswer(invocation -> {
            Item savedItem = invocation.getArgument(0);
            setItemId(savedItem, itemId);
            return savedItem;
        });

        // Act
        var result = createItemInteractor.execute(createItemInput);

        // Assert
        assertNotNull(result);
        assertEquals(itemId, result.id());
        assertEquals(name, result.name());
        assertEquals(description, result.description());
        assertEquals(price, result.price());
        assertEquals(availability, result.availability());
        assertEquals(photo, result.photo());

        verify(itemRepositoryGateway).save(any(Item.class));
    }

    @Test
    @DisplayName("Deve criar um item com availability LOCAL_AND_DELIVERY")
    void shouldCreateItemWithLocalAndDeliveryAvailability() {
        // Arrange
        String name = "Item Local e Delivery";
        String description = "Descrição do Item";
        BigDecimal price = new BigDecimal("45.50");
        String availability = "LOCAL_AND_DELIVERY";
        String photo = "url_foto.jpg";
        Long itemId = 4L;

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .build();

        // Simular o ID sendo definido após o save
        setItemId(item, itemId);

        when(itemRepositoryGateway.save(any(Item.class))).thenAnswer(invocation -> {
            Item savedItem = invocation.getArgument(0);
            setItemId(savedItem, itemId);
            return savedItem;
        });

        // Act
        var result = createItemInteractor.execute(createItemInput);

        // Assert
        assertNotNull(result);
        assertEquals(itemId, result.id());
        assertEquals(name, result.name());
        assertEquals(description, result.description());
        assertEquals(price, result.price());
        assertEquals(availability, result.availability());
        assertEquals(photo, result.photo());

        verify(itemRepositoryGateway).save(any(Item.class));
    }

    /**
     * Método auxiliar para definir o ID do item usando reflexão
     */
    private void setItemId(Item item, Long id) {
        try {
            var field = Item.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(item, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao definir ID do item", e);
        }
    }
}