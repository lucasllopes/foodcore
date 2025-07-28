package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.domain.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(CreateUserInput input, String encodedPassword, UserTypeDomain tipo) {
        return User.builder()
                .nome(input.nome())
                .email(input.email())
                .login(input.login())
                .senha(encodedPassword)
                .tipo(tipo)
                .address(input.enderecos().stream()
                        .map(enderecoInput -> Address.builder()
                                .logradouro(enderecoInput.logradouro())
                                .numero(enderecoInput.numero())
                                .complemento(enderecoInput.complemento())
                                .bairro(enderecoInput.bairro())
                                .cidade(enderecoInput.cidade())
                                .estado(enderecoInput.estado())
                                .cep(enderecoInput.cep())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static User toDomain(User existing, UpdateUserInput input) {
        existing.updateInformation(
                input.nome(),
                input.email(),
                input.enderecos().stream()
                        .map(enderecoInput -> Address.builder()
                                .logradouro(enderecoInput.logradouro())
                                .numero(enderecoInput.numero())
                                .complemento(enderecoInput.complemento())
                                .bairro(enderecoInput.bairro())
                                .cidade(enderecoInput.cidade())
                                .estado(enderecoInput.estado())
                                .cep(enderecoInput.cep())
                                .build())
                        .collect(Collectors.toList())
        );
        return existing;
    }

    public static User fromCreateInput(String encodedPassword, UserTypeDomain tipo, CreateUserInput input) {
        return toDomain(input, encodedPassword, tipo);
    }

    public static CreateUserOutput fromDomain(User user) {
        return new CreateUserOutput(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getTipo(),
                user.getAddress().stream()
                        .map(endereco -> new AddressOutput(
                                endereco.getLogradouro(),
                                endereco.getNumero(),
                                endereco.getComplemento(),
                                endereco.getBairro(),
                                endereco.getCep(),
                                endereco.getEstado(),
                                endereco.getCidade()
                        ))
                        .collect(Collectors.toList()),
                user.getUserSubtype() != null
                        ? new CreateUserSubtypeOutput(user.getUserSubtype().getId(), user.getUserSubtype().getName())
                        : null
        );
    }
}
