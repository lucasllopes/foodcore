package com.fiap.foodcore.application.usecase.output;

import com.fiap.foodcore.domain.UserTypeDomain;

import java.util.List;

public record CreateUserOutput(Long id,
                               String nome,
                               String email,
                               String login,
                               UserTypeDomain tipo,
                               List<AddressOutput> enderecos,
                               CreateUserSubtypeOutput userTypeOutput) {
}
