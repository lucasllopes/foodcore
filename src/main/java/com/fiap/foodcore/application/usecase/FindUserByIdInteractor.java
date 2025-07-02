package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.infrastructure.gateways.mapper.UserDtoMapper;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.exception.DataNotFoundException;

public class FindUserByIdInteractor {

    private final UserGateway userGateway;

    public FindUserByIdInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserResponseDTO execute(Long id) {

        return userGateway.findById(id)
                .map(UserDtoMapper::toResponseDto)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
    }
}
