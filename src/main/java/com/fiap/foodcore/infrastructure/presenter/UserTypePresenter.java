package com.fiap.foodcore.infrastructure.presenter;

import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserTypeRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserTypeResponseDTO;

public class UserTypePresenter {

    public static CreateUserTypeInput toInputCreate(UserTypeRequestDTO dto) {
        return new CreateUserTypeInput(dto.name());
    }

    public static UserTypeResponseDTO toDto(CreateUserTypeOutput output) {

        return new UserTypeResponseDTO(
                output.id(),
                output.name());
    }
}
