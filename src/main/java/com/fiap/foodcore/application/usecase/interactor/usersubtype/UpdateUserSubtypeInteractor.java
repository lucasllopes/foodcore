package com.fiap.foodcore.application.usecase.interactor.usersubtype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.UpdateUserSubtypeUseCase;
import com.fiap.foodcore.application.usecase.input.UpdateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.mapper.UserSubtypeMapper;
import com.fiap.foodcore.application.usecase.output.UpdateUserSubtypeOutput;

public class UpdateUserSubtypeInteractor implements UpdateUserSubtypeUseCase {

    private final UserSubtypeGateway gateway;

    public UpdateUserSubtypeInteractor(UserSubtypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public UpdateUserSubtypeOutput execute(Long id, UpdateUserSubtypeInput input) {
        var existing = gateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));

        var existingUserTypes = gateway.findByNameIgnoreCase(input.name());

        boolean existsDuplicate = existingUserTypes.stream()
                .anyMatch(u -> !u.getId().equals(id));

        if (existsDuplicate) {
            throw new DuplicatedDataException("Esse tipo de usuário já está cadastrado");
        }

        existing.update(input.name());

        var saved = gateway.save(existing);
        return UserSubtypeMapper.fromUpdateDomain(saved);
    }
}
