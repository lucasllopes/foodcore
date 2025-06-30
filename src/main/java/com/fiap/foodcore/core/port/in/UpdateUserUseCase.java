package com.fiap.foodcore.core.port.in;

import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;

public interface UpdateUserUseCase {
    UserResponseDTO execute(Long id, UserUpdateRequestDTO dto);
}
