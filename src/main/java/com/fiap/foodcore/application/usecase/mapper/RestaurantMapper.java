package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;

import java.util.stream.Collectors;

public class RestaurantMapper {

    public static Restaurant toDomain(CreateRestaurantInput input) {
        return Restaurant.create(input);
    }

    public static CreateRestaurantOutput fromDomain(Restaurant restaurant) {
        return new CreateRestaurantOutput(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress().stream()
                        .map(endereco -> new AddressOutput(
                                endereco.getLogradouro(),
                                endereco.getNumero(),
                                endereco.getComplemento(),
                                endereco.getBairro(),
                                endereco.getCep(),
                                endereco.getEstado(),
                                endereco.getCidade()
                        ))
                        .collect(Collectors.toList()),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getClosingHours(),
                restaurant.getOwnerId()
        );
    }
}
