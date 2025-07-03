package com.fiap.foodcore.application.service.strategy;


import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;

public interface CreateUserStrategy {

    UserResponseDTO create(UserCreateRequestDTO dto);

}
