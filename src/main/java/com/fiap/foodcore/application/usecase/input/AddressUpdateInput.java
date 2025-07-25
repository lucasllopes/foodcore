package com.fiap.foodcore.application.usecase.input;

public record AddressUpdateInput(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep
) {}