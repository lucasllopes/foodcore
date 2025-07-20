package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.domain.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(CreateUserInput input, String encodedPassword, UserTypeDomain tipo) {
        return User.create(encodedPassword, tipo, input);
    }

    public static User toDomain(User existing, UpdateUserInput input) {
        existing.updateInformation(input);
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
                user.getUserType() != null
                        ? new CreateUserTypeOutput(user.getUserType().getId(), user.getUserType().getName())
                        : null
        );
    }
}
