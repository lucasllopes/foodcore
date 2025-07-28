package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.AddressUpdateInput;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class AddressTest {

    @Test
    void deveCriarEnderecoComSucesso() {
        CreateAddressInput input = new CreateAddressInput(
                "Rua A", "123", "Apto 1", "Centro", "São Paulo", "11111-000", "SP"
        );

        Address address = Address.builder()
                .logradouro(input.logradouro())
                .numero(input.numero())
                .complemento(input.complemento())
                .bairro(input.bairro())
                .cidade(input.cidade())
                .estado(input.estado())
                .cep(input.cep())
                .build();

        assertEquals("Rua A", address.getLogradouro());
        assertEquals("123", address.getNumero());
        assertEquals("Apto 1", address.getComplemento());
        assertEquals("Centro", address.getBairro());
        assertEquals("São Paulo", address.getCidade());
        assertEquals("SP", address.getEstado());
        assertEquals("11111-000", address.getCep());
    }

    @Test
    void deveAtualizarEnderecoComSucesso() {
        AddressUpdateInput input = new AddressUpdateInput(
                "Rua A", "123", "Complemento", "Bairro B", "Rio de Janeiro", "RJ", "11111-111"
        );

        Address address = Address.builder()
                .logradouro(input.logradouro())
                .numero(input.numero())
                .complemento(input.complemento())
                .bairro(input.bairro())
                .cidade(input.cidade())
                .estado(input.estado())
                .cep(input.cep())
                .build();

        assertEquals("Rua A", address.getLogradouro());
        assertEquals("123", address.getNumero());
        assertEquals("Complemento", address.getComplemento());
        assertEquals("Bairro B", address.getBairro());
        assertEquals("Rio de Janeiro", address.getCidade());
        assertEquals("RJ", address.getEstado());
        assertEquals("11111-111", address.getCep());
    }

    @Test
    void deveAdicionarNovoEnderecoComSucesso() {
        AddressUpdateInput inputInicial = new AddressUpdateInput(
                "Rua A", "123", "Complemento", "Paulista", "Sao Paulo", "SP", "11111-111"
        );
        Address address = Address.builder()
                .logradouro(inputInicial.logradouro())
                .numero(inputInicial.numero())
                .complemento(inputInicial.complemento())
                .bairro(inputInicial.bairro())
                .cidade(inputInicial.cidade())
                .estado(inputInicial.estado())
                .cep(inputInicial.cep())
                .build();

        AddressUpdateInput novoInput = new AddressUpdateInput(
                "Rua B", "456", "Complemento 2", "Bairro", "Curitiba", "PR", "22222-222"
        );
        Address updatedAddress = Address.builder()
                .logradouro(novoInput.logradouro())
                .numero(novoInput.numero())
                .complemento(novoInput.complemento())
                .bairro(novoInput.bairro())
                .cidade(novoInput.cidade())
                .estado(novoInput.estado())
                .cep(novoInput.cep())
                .build();

        address = updatedAddress;

        assertEquals("Rua B", address.getLogradouro());
        assertEquals("456", address.getNumero());
        assertEquals("Complemento 2", address.getComplemento());
        assertEquals("Bairro", address.getBairro());
        assertEquals("Curitiba", address.getCidade());
        assertEquals("PR", address.getEstado());
        assertEquals("22222-222", address.getCep());
    }

    @Test
    void deveRetornarEnderecoComSucesso() {

        Address address = Address.rebuildAddress(
                1L, "Rua 1", "123", "Complemento 1", "Paulista", "Sao Paulo", "SP", "11111-111"
        );

        assertEquals(1L, address.getId());
        assertEquals("Rua 1", address.getLogradouro());
        assertEquals("123", address.getNumero());
        assertEquals("Complemento 1", address.getComplemento());
        assertEquals("Paulista", address.getBairro());
        assertEquals("Sao Paulo", address.getCidade());
        assertEquals("SP", address.getEstado());
        assertEquals("11111-111", address.getCep());
    }
}
