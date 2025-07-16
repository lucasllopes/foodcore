package com.fiap.foodcore.infrastructure.presenter;

import com.fiap.foodcore.application.usecase.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.*;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.infrastructure.web.controller.dto.RestaurantCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantPresenter {

    public static RestaurantResponseDTO toDto(CreateRestaurantOutput output) {
        return new RestaurantResponseDTO(
                output.id(),
                output.nome(),
                output.address().stream().map(end ->
                        new AddressResponseDTO(
                                end.logradouro(),
                                end.numero(),
                                end.complemento(),
                                end.bairro(),
                                end.cep(),
                                end.estado(),
                                end.cidade()
                        )
                ).collect(Collectors.toList()),
                output.cuisineType(),
                output.openingHours(),
                output.closingHours(),
                output.ownerId()
        );
    }

    public static List<RestaurantResponseDTO> toDtoList(List<CreateRestaurantOutput> outputs) {
        return outputs.stream().map(RestaurantPresenter::toDto).collect(Collectors.toList());
    }

    public static CreateRestaurantInput toInputCreate(RestaurantCreateRequestDTO dto) {
        List<CreateAddressInput> addressesInput = dto.enderecos().stream()
                .map(addressDto -> new CreateAddressInput(
                        addressDto.logradouro(),
                        addressDto.numero(),
                        addressDto.complemento(),
                        addressDto.bairro(),
                        addressDto.cidade(),
                        addressDto.cep(),
                        addressDto.estado()
                ))
                .toList();

        return new CreateRestaurantInput(
                dto.nome(),
                addressesInput,
                dto.cuisineType(),
                dto.openingHours(),
                dto.closingHours(),
                dto.ownerId()
        );
    }

    public static UpdateRestaurantInput toInputUpdate(RestaurantUpdateRequestDTO dto) {
        List<AddressUpdateInput> addressesInput = dto.enderecos().stream()
                .map(addressDto -> new AddressUpdateInput(
                        addressDto.logradouro(),
                        addressDto.numero(),
                        addressDto.complemento(),
                        addressDto.bairro(),
                        addressDto.cidade(),
                        addressDto.cep(),
                        addressDto.estado()
                ))
                .toList();

        return new UpdateRestaurantInput(
                dto.name(),
                addressesInput,
                dto.cuisineType(),
                dto.openingHours(),
                dto.closingHours()
        );
    }
}
