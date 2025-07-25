package com.fiap.foodcore.application.usecase.input;

import com.fiap.foodcore.domain.UserTypeDomain;

import java.util.List;

public record CreateUserInput(
        String nome,
        String email,
        String login,
        String senha,
        UserTypeDomain tipo,
        List<CreateAddressInput> enderecos
) {}