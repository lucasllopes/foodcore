package com.fiap.foodcore.application.usecase.output;

public record AddressOutput(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String cep,
        String estado
) {}