package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.adapter.in.web.mapper.UserDtoMapper;
import com.fiap.foodcore.core.port.in.UpdateUserUseCase;
import com.fiap.foodcore.core.port.out.UserRepositoryPort;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import com.fiap.foodcore.exception.DataNotFoundException;
import com.fiap.foodcore.exception.DuplicatedDataException;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserInteractor implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UpdateUserInteractor(UserRepositoryPort userGateway) {
        this.userRepositoryPort = userGateway;
    }

    @Override
    public UserResponseDTO execute(Long id, UserUpdateRequestDTO dto) {
        var existing = userRepositoryPort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        userRepositoryPort.findByEmail(dto.email())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new DuplicatedDataException("Email já em uso"); });

        var domain = UserDtoMapper.toDomain(existing, dto);
        var saved = userRepositoryPort.save(domain);
        return UserDtoMapper.toResponseDto(saved);
    }
}
