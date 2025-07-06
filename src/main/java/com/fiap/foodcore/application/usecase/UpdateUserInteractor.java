package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;

public class UpdateUserInteractor {

    private final UserGateway userGateway;

    public UpdateUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public CreateUserOutput execute(Long id, UpdateUserInput input) {
        var existing = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        userGateway.findByEmail(input.email())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new DuplicatedDataException("Email já em uso"); });

        var domain = UserMapper.toDomain(existing, input);
        var saved = userGateway.save(domain);
        return UserMapper.fromDomain(saved);
    }
}
