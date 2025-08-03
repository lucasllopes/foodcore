package com.fiap.foodcore.infrastructure.presenter;

import com.fiap.foodcore.application.usecase.input.CreateMenuInput;
import com.fiap.foodcore.application.usecase.input.CreateItemInput;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuUpdateRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MenuPresenter {

    public static MenuCreateOutput fromCreateRequestDTO(MenuCreateRequestDTO dto) {
        if (dto == null) return null;
        return new MenuCreateOutput(
                null, // id normalmente gerado pelo sistema
                dto.name(),
                dto.description(),
                dto.restaurantId(),
                dto.items().stream().map(
                        item -> new ItemCreateOutput(
                                null, // id normalmente gerado pelo sistema
                                item.name(),
                                item.description(),
                                item.price(),
                                item.availability(),
                                item.photo()
                        )
                ).collect(Collectors.toList()
                )
        );
    }

    public static MenuCreateRequestDTO toCreateRequestDTO(MenuCreateOutput output) {
        if (output == null) return null;
        return new MenuCreateRequestDTO(
                output.name(),
                output.description(),
                output.restaurantId(),
                output.items() == null ? List.of() :
                        output.items().stream().map(item -> new ItemCreateRequestDTO(
                                item.id(),
                                item.name(),
                                item.description(),
                                item.price(),
                                item.availability(),
                                item.photo()
                        )).collect(Collectors.toList())
        );
    }

    public static MenuResponseDTO toResponseDTO(MenuCreateOutput output) {
        if (output == null) return null;
        return new MenuResponseDTO(
                output.id(),
                output.name(),
                output.description(),
                output.restaurantId(),
                output.items() == null ? List.of() :
                        output.items().stream()
                                .map(item -> new ItemCreateResponseDTO(
                                        item.id(),
                                        item.name(),
                                        item.description(),
                                        item.price(),
                                        item.availability(),
                                        item.photo()
                                )).collect(Collectors.toList())
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
                dto.description(),
                dto.restaurantId(),
                dto.items() == null ? List.of() :
                        dto.items().stream()
                                .map(item -> new CreateItemInput(
                                        item.id(),
                                        item.name(),
                                        item.description(),
                                        item.price(),
                                        item.availability(),
                                        item.photo()
                                )).collect(Collectors.toList())
        );
    }



    public static CreateMenuInput fromUpdateInputRequestDTO(MenuUpdateRequestDTO dto) {
        if (dto == null) return null;
        return new CreateMenuInput(
                dto.name(),
                dto.description(),
                dto.restaurantId(),
                dto.items().stream()
                        .map(item -> new CreateItemInput(
                                item.id(),
                                item.name(),
                                item.description(),
                                item.price(),
                                item.availability(),
                                item.photo()
                        )).collect(Collectors.toList())
        );
    }


}
