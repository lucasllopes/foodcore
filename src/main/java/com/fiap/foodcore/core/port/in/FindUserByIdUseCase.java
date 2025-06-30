package com.fiap.foodcore.core.port.in;

import com.fiap.foodcore.dto.UserResponseDTO;

public interface FindUserByIdUseCase {
    UserResponseDTO execute(Long id);
}
