package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.input.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;

import java.util.stream.Collectors;

public class RestaurantMapper {

    public static Restaurant toDomain(CreateRestaurantInput input) {
        return Restaurant.create(input);
    }
    public static Restaurant toDomain(Restaurant restaurant, UpdateRestaurantInput input) {
        restaurant.updateInformation(input);
        return restaurant;
    }

    public static CreateRestaurantOutput fromDomain(Restaurant restaurant) {
        AddressOutput addressOutput = new AddressOutput(
                restaurant.getAddress().getLogradouro(),
                restaurant.getAddress().getNumero(),
                restaurant.getAddress().getComplemento(),
                restaurant.getAddress().getBairro(),
                restaurant.getAddress().getCep(),
                restaurant.getAddress().getEstado(),
                restaurant.getAddress().getCidade()
        );
        return new CreateRestaurantOutput(
                restaurant.getId(),
                restaurant.getName(),
                addressOutput,
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getClosingHours(),
                restaurant.getOwnerId()
        );
    }
}
