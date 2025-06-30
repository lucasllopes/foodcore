package com.fiap.foodcore.core.port.in;

import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;

public interface CreateUsersUseCase {
    UserResponseDTO execute(UserCreateRequestDTO dto);
}
