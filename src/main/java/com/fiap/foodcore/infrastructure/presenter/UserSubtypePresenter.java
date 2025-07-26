package com.fiap.foodcore.infrastructure.presenter;

import com.fiap.foodcore.application.usecase.input.CreateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserSubtypeOutput;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;

public class UserSubtypePresenter {

    public static CreateUserSubtypeInput toInputCreate(UserSubtypeRequestDTO dto) {
        return new CreateUserSubtypeInput(dto.name());
    }

    public static UserSubtypeResponseDTO toDto(CreateUserSubtypeOutput output) {

        return new UserSubtypeResponseDTO(
                output.id(),
                output.name().toUpperCase());
    }

    public static UpdateUserSubtypeInput toInputUpdate(UserSubtypeUpdateRequestDTO dto) {
        UpdateUserSubtypeInput updateUserInput = new UpdateUserSubtypeInput(dto.name().toUpperCase());
        return updateUserInput;
    }

    public static UpdateUserSubtypeResponseDTO toUpdateDto(UpdateUserSubtypeOutput output) {
        return new UpdateUserSubtypeResponseDTO(output.id(), output.name().toUpperCase(), output.lastModified());
    }
}
