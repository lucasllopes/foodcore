package com.fiap.foodcore.infrastructure.presenter;

import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuResponseDTO;

import java.util.List;

public class MenuPresenter {

    public static MenuCreateOutput fromCreateRequestDTO(MenuCreateRequestDTO dto) {
        if (dto == null) return null;
        return new MenuCreateOutput(
                null, // id normalmente gerado pelo sistema
                dto.name(),
                dto.description(),
                null // itens podem ser adicionados depois
        );
    }

    public static MenuCreateRequestDTO toCreateRequestDTO(MenuCreateOutput output) {
        if (output == null) return null;
        return new MenuCreateRequestDTO(
                output.name(),
                output.description()
        );
    }



    public static MenuResponseDTO toResponseDTO(MenuCreateOutput output) {
        if (output == null) return null;
        return new MenuResponseDTO(
                output.name(),
                output.description()
        );
    }

    public static List<MenuResponseDTO> toDtoList(List<MenuCreateOutput> outputs) {
        if (outputs == null) return null;
        return outputs.stream()
                .map(MenuPresenter::toResponseDTO)
                .toList();
    }

    public static CreateMenuInput fromCreateInputRequestDTO(MenuCreateRequestDTO dto) {
        if (dto == null) return null;
        return new CreateMenuInput(
                dto.name(),
                dto.description()
        );
    }


}
