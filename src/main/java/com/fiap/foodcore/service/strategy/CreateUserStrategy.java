package com.fiap.foodcore.service.strategy;


import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;

public interface CreateUserStrategy {

    UserResponseDTO create(UserCreateRequestDTO dto);

}
