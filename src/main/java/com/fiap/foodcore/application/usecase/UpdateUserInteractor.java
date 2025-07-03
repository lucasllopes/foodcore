package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.infrastructure.gateways.mapper.UserDtoMapper;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserUpdateRequestDTO;
import com.fiap.foodcore.exception.DataNotFoundException;
import com.fiap.foodcore.exception.DuplicatedDataException;

public class UpdateUserInteractor {

    private final UserGateway userGateway;

    public UpdateUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserResponseDTO execute(Long id, UserUpdateRequestDTO dto) {
        var existing = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        userGateway.findByEmail(dto.email())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new DuplicatedDataException("Email já em uso"); });

        var domain = UserDtoMapper.toDomain(existing, dto);
        var saved = userGateway.save(domain);
        return UserDtoMapper.toResponseDto(saved);
    }
}
