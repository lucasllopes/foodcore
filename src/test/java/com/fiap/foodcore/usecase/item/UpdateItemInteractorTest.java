package com.fiap.foodcore.usecase.item;


import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.application.usecase.input.UpdateItemInput;
import com.fiap.foodcore.application.usecase.item.UpdateItemInteractor;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateItemInteractorTest {

    @Mock
    private ItemGateway itemGateway;

    private UpdateItemInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new UpdateItemInteractor(itemGateway);
    }

    @Test
    @DisplayName("Deve atualizar item com sucesso")
    void deveAtualizarItemComSucesso() {
        Long id = 1L;
        Long ownerId = 1L;
        UpdateItemInput input = new UpdateItemInput("Novo", "Desc", new BigDecimal("10.00"), "LOCAL", "foto.jpg");
        Item existente = mock(Item.class);
        Item atualizado = mock(Item.class);
        Item salvo = mock(Item.class);
        User owner = mock(User.class);

        when(itemGateway.findById(id)).thenReturn(Optional.of(existente));
        when(itemGateway.findByName(input.name())).thenReturn(Optional.empty());
        when(existente.getOwnerId()).thenReturn(owner);
        when(owner.getId()).thenReturn(ownerId);
        when(existente.atualizarInformacoes(
                input.name(), input.description(), input.price(), input.availability(), input.photo(), owner
        )).thenReturn(atualizado);
        when(itemGateway.save(any(Item.class))).thenReturn(salvo);

        when(salvo.getId()).thenReturn(id);
        when(salvo.getName()).thenReturn("Novo");
        when(salvo.getDescription()).thenReturn("Desc");
        when(salvo.getPrice()).thenReturn(new BigDecimal("10.00"));
        when(salvo.getAvailability()).thenReturn("LOCAL");
        when(salvo.getPhoto()).thenReturn("foto.jpg");
        when(salvo.getOwnerId()).thenReturn(owner);

        ItemCreateOutput output = interactor.execute(id, input);

        assertNotNull(output);
        assertEquals(id, output.id());
        assertEquals("Novo", output.name());
        assertEquals("Desc", output.description());
        assertEquals(new BigDecimal("10.00"), output.price());
        assertEquals("LOCAL", output.availability());
        assertEquals("foto.jpg", output.photo());
        assertEquals(ownerId, output.ownerId());

        verify(itemGateway).findById(id);
        verify(itemGateway).findByName(input.name());
        verify(existente).atualizarInformacoes(
                input.name(), input.description(), input.price(), input.availability(), input.photo(), owner
        );
        verify(itemGateway).save(any(Item.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se item não encontrado")
    void deveLancarExcecaoSeItemNaoEncontrado() {
        Long id = 2L;
        UpdateItemInput input = new UpdateItemInput("Nome", "Desc", new BigDecimal("5.00"), "DELIVERY", "foto2.jpg");
        when(itemGateway.findById(id)).thenReturn(Optional.empty());

        DataNotFoundException ex = assertThrows(DataNotFoundException.class, () -> interactor.execute(id, input));
        assertEquals("Item não encontrado", ex.getMessage());

        verify(itemGateway).findById(id);
        verify(itemGateway, never()).findByName(anyString());
        verify(itemGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção se nome já em uso por outro item")
    void deveLancarExcecaoSeNomeDuplicado() {
        Long id = 3L;
        UpdateItemInput input = new UpdateItemInput("Duplicado", "Desc", new BigDecimal("7.00"), "LOCAL", "foto3.jpg");
        Item existente = mock(Item.class);
        Item outro = mock(Item.class);

        when(itemGateway.findById(id)).thenReturn(Optional.of(existente));
        when(itemGateway.findByName(input.name())).thenReturn(Optional.of(outro));
        when(outro.getId()).thenReturn(99L);

        DuplicatedDataException ex = assertThrows(DuplicatedDataException.class, () -> interactor.execute(id, input));
        assertEquals("Nome item já em uso", ex.getMessage());

        verify(itemGateway).findById(id);
        verify(itemGateway).findByName(input.name());
        verify(itemGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve permitir atualizar se nome for do próprio item")
    void devePermitirAtualizarSeNomeDoProprioItem() {
        Long id = 4L;
        Long ownerId = 4L;
        UpdateItemInput input = new UpdateItemInput("MesmoNome", "Desc", new BigDecimal("12.00"), "DELIVERY", "foto4.jpg");
        Item existente = mock(Item.class);
        User owner = mock(User.class);

        when(itemGateway.findById(id)).thenReturn(Optional.of(existente));
        when(itemGateway.findByName(input.name())).thenReturn(Optional.of(existente));
        when(existente.getId()).thenReturn(id);
        when(existente.getOwnerId()).thenReturn(owner);
        when(owner.getId()).thenReturn(ownerId);

        Item atualizado = mock(Item.class);
        Item salvo = mock(Item.class);

        when(existente.atualizarInformacoes(
                input.name(), input.description(), input.price(), input.availability(), input.photo(), owner
        )).thenReturn(atualizado);
        when(itemGateway.save(any(Item.class))).thenReturn(salvo);

        when(salvo.getId()).thenReturn(id);
        when(salvo.getName()).thenReturn("MesmoNome");
        when(salvo.getDescription()).thenReturn("Desc");
        when(salvo.getPrice()).thenReturn(new BigDecimal("12.00"));
        when(salvo.getAvailability()).thenReturn("DELIVERY");
        when(salvo.getPhoto()).thenReturn("foto4.jpg");
        when(salvo.getOwnerId()).thenReturn(owner);

        ItemCreateOutput output = interactor.execute(id, input);

        assertNotNull(output);
        assertEquals(id, output.id());
        assertEquals("MesmoNome", output.name());
        assertEquals("Desc", output.description());
        assertEquals(new BigDecimal("12.00"), output.price());
        assertEquals("DELIVERY", output.availability());
        assertEquals("foto4.jpg", output.photo());
        assertEquals(ownerId, output.ownerId());

        verify(itemGateway).findById(id);
        verify(itemGateway).findByName(input.name());
        verify(existente).atualizarInformacoes(
                input.name(), input.description(), input.price(), input.availability(), input.photo(), owner
        );
        verify(itemGateway).save(any(Item.class));
    }
}
