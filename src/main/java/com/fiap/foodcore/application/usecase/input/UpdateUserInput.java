package com.fiap.foodcore.application.usecase.input;

import java.util.List;

public record UpdateUserInput(
        String nome,
        String email,
        List<AddressUpdateInput> enderecos
) {}