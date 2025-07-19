package com.fiap.foodcore.infrastructure.presenter;

import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserTypeOutput;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;

public class UserTypePresenter {

    public static CreateUserTypeInput toInputCreate(UserTypeRequestDTO dto) {
        return new CreateUserTypeInput(dto.name());
    }

    public static UserTypeResponseDTO toDto(CreateUserTypeOutput output) {

        return new UserTypeResponseDTO(
                output.id(),
                output.name().toUpperCase());
    }

    public static UpdateUserTypeInput toInputUpdate(UserTypeUpdateRequestDTO dto) {
        UpdateUserTypeInput updateUserInput = new UpdateUserTypeInput(dto.name().toUpperCase());
        return updateUserInput;
    }

    public static UpdateUserTypeResponseDTO toUpdateDto(UpdateUserTypeOutput output) {
        return new UpdateUserTypeResponseDTO(output.id(), output.name().toUpperCase(), output.lastModified());
    }
}
