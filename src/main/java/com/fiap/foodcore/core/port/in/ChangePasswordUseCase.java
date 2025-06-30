package com.fiap.foodcore.core.port.in;

import com.fiap.foodcore.dto.ChangePasswordRequestDTO;

public interface ChangePasswordUseCase {
    void execute(Long id, ChangePasswordRequestDTO dto);
}
