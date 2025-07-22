package com.fiap.foodcore.infrastructure.presenter;


import com.fiap.foodcore.application.usecase.input.AddressUpdateInput;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.input.restaurant.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.restaurant.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.infrastructure.web.controller.dto.AddressResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantUpdateRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantPresenter {

    public static RestaurantResponseDTO toDto(CreateRestaurantOutput output) {
        AddressResponseDTO addressResponseDTO = getAddressResponseDTO(output.address());
        return new RestaurantResponseDTO(
                output.id(),
                output.nome(), addressResponseDTO,
                output.cuisineType(),
                output.openingHours(),
                output.closingHours(),
                output.ownerId()
        );
    }
    private static AddressResponseDTO getAddressResponseDTO(AddressOutput addressOutput){
        return new AddressResponseDTO(
                addressOutput.logradouro(),
                addressOutput.numero(),
                addressOutput.complemento(),
                addressOutput.bairro(),
                addressOutput.cep(),
                addressOutput.estado(),
                addressOutput.cidade()
        );
    }

    public static List<RestaurantResponseDTO> toDtoList(List<CreateRestaurantOutput> outputs) {
        return outputs.stream().map(RestaurantPresenter::toDto).collect(Collectors.toList());
    }

    public static CreateRestaurantInput toInputCreate(RestaurantCreateRequestDTO dto) {
        CreateAddressInput addressInput =  new CreateAddressInput(
                dto.endereco().logradouro(),
                dto.endereco().numero(),
                dto.endereco().complemento(),
                dto.endereco().bairro(),
                dto.endereco().cidade(),
                dto.endereco().cep(),
                dto.endereco().estado());

        return new CreateRestaurantInput(
                dto.nome(),
                addressInput,
                dto.cuisineType(),
                dto.openingHours(),
                dto.closingHours(),
                dto.ownerId()
        );
    }

    public static UpdateRestaurantInput toInputUpdate(RestaurantUpdateRequestDTO dto) {
        AddressUpdateInput addressInput = new AddressUpdateInput(
                dto.endereco().logradouro(),
                dto.endereco().numero(),
                dto.endereco().complemento(),
                dto.endereco().bairro(),
                dto.endereco().cidade(),
                dto.endereco().cep(),
                dto.endereco().estado()
        );

        return new UpdateRestaurantInput(
                dto.name(),
                addressInput,
                dto.cuisineType(),
                dto.openingHours(),
                dto.closingHours()
        );
    }
}
