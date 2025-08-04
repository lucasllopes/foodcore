package com.fiap.foodcore.usecase.item;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.item.CreateItemInteractor;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.infrastructure.gateways.ItemRepositoryGateway;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateItemInteractorTest {

    @Mock
    private ItemRepositoryGateway itemRepositoryGateway;

    @Mock
    private UserGateway userGateway;

    private CreateItemInteractor createItemInteractor;

    @BeforeEach
    void setUp() {
        createItemInteractor = new CreateItemInteractor(itemRepositoryGateway, userGateway);
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
        Long ownerId = 1L;
        User ownerUser = mock(User.class);

        // Configurar mock do UserGateway para retornar um usuário válido
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(mock(User.class)));

        // Verificar que não há item com o mesmo nome
        when(itemRepositoryGateway.findByName(name)).thenReturn(Optional.empty());

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo,
                ownerId
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .ownerId(ownerUser)  // Agora incluímos o ownerId
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
        assertEquals(ownerId, result.ownerId());

        // Verify
        verify(userGateway).findById(ownerId);
        verify(itemRepositoryGateway).findByName(name);

        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryGateway).save(itemCaptor.capture());

        Item savedItem = itemCaptor.getValue();
        assertEquals(name, savedItem.getName());
        assertEquals(description, savedItem.getDescription());
        assertEquals(price, savedItem.getPrice());
        assertEquals(availability, savedItem.getAvailability());
        assertEquals(photo, savedItem.getPhoto());
        assertEquals(ownerId, savedItem.getOwnerId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o proprietário não existe")
    void shouldThrowExceptionWhenOwnerNotExists() {
        // Arrange
        String name = "Item de Teste";
        String description = "Descrição do Item";
        BigDecimal price = new BigDecimal("25.50");
        String availability = "LOCAL";
        String photo = "url_da_foto.jpg";
        Long ownerId = 999L;

        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());
        when(itemRepositoryGateway.findByName(name)).thenReturn(Optional.empty());

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo,
                ownerId
        );

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> createItemInteractor.execute(createItemInput));
        verify(userGateway).findById(ownerId);
        verify(itemRepositoryGateway).findByName(name);
        verify(itemRepositoryGateway, never()).save(any(Item.class));
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
        Long ownerId = 2L;
        User ownerUser = mock(User.class);

        // Configurar mock do UserGateway para retornar um usuário válido
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(mock(User.class)));
        when(itemRepositoryGateway.findByName(name)).thenReturn(Optional.empty());

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo,
                ownerId
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .ownerId(ownerUser)
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
        assertEquals(ownerId, result.ownerId());

        verify(userGateway).findById(ownerId);
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
        Long ownerId = 3L;
        User ownerUser = mock(User.class);

        // Configurar mock do UserGateway para retornar um usuário válido
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(mock(User.class)));
        when(itemRepositoryGateway.findByName(name)).thenReturn(Optional.empty());

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo,
                ownerId
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .ownerId(ownerUser)
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
        assertEquals(ownerId, result.ownerId());

        verify(userGateway).findById(ownerId);
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
        Long ownerId = 4L;
        User ownerUser = mock(User.class);

        // Configurar mock do UserGateway para retornar um usuário válido
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(mock(User.class)));
        when(itemRepositoryGateway.findByName(name)).thenReturn(Optional.empty());

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo,
                ownerId
        );

        var item = new Item.Builder()
                .name(name)
                .description(description)
                .price(price)
                .availability(availability)
                .photo(photo)
                .ownerId(ownerUser)
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
        assertEquals(ownerId, result.ownerId());

        verify(userGateway).findById(ownerId);
        verify(itemRepositoryGateway).save(any(Item.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ownerId for nulo")
    void shouldThrowExceptionWhenOwnerIdIsNull() {
        // Arrange
        String name = "Item de Teste";
        String description = "Descrição do Item";
        BigDecimal price = new BigDecimal("25.50");
        String availability = "LOCAL";
        String photo = "url_da_foto.jpg";
        Long ownerId = null;

        when(itemRepositoryGateway.findByName(name)).thenReturn(Optional.empty());

        var createItemInput = new CreateItemInput(
                null,
                name,
                description,
                price,
                availability,
                photo,
                ownerId
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> createItemInteractor.execute(createItemInput));
        verify(itemRepositoryGateway).findByName(name);
        verify(userGateway, never()).findById(anyLong());
        verify(itemRepositoryGateway, never()).save(any(Item.class));
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