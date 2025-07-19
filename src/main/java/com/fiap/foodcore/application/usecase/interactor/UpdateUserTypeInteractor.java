package com.fiap.foodcore.application.usecase.interactor;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.UpdateUserTypeUseCase;
import com.fiap.foodcore.application.usecase.input.UpdateUserTypeInput;
import com.fiap.foodcore.application.usecase.mapper.UserTypeMapper;
import com.fiap.foodcore.application.usecase.output.UpdateUserTypeOutput;
import com.fiap.foodcore.domain.UserType;

public class UpdateUserTypeInteractor implements UpdateUserTypeUseCase {

    private final UserTypeGateway gateway;

    public UpdateUserTypeInteractor(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public UpdateUserTypeOutput execute(Long id, UpdateUserTypeInput input) {
        var existing = gateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));

        gateway.findByNameIgnoreCase(input.name())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new DuplicatedDataException("Esse tipo de usuário já está cadastrado"); });

        existing.update(input.name());

        var saved = gateway.save(existing);
        return UserTypeMapper.fromUpdateDomain(saved);
    }
}
