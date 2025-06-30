package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.adapter.in.web.mapper.UserDtoMapper;
import com.fiap.foodcore.core.port.in.FindUserByIdUseCase;
import com.fiap.foodcore.core.port.out.UserRepositoryPort;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindUserByIdInteractor implements FindUserByIdUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public FindUserByIdInteractor(UserRepositoryPort userGateway) {
        this.userRepositoryPort = userGateway;
    }

    @Override
    public UserResponseDTO execute(Long id) {
        return userRepositoryPort.findById(id)
                .map(UserDtoMapper::toResponseDto)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
    }
}
