package com.fiap.foodcore.application.usecase.input;

public record CreateAddressInput(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String cep,
        String estado
) {}