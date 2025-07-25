package com.fiap.foodcore.application.usecase.mapper;

import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.input.restaurant.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.restaurant.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.AddressOutput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.builder.AddressBuilder;
import com.fiap.foodcore.domain.builder.RestaurantBuilder;

public class RestaurantMapper {

    public static Restaurant toDomain(CreateRestaurantInput input) {
        return RestaurantBuilder.getInstance()
                .withName(input.name())
                .withAddress(getAddressDomain(input.address()))
                .withCuisineType(input.cuisineType())
                .withOpeningHours(input.openingHours())
                .withClosingHours(input.closingHours())
                .withOwnerId(input.ownerId()).build();
    }
    private static Address getAddressDomain(CreateAddressInput input) {
        if (input == null) {
            return null;
        }
        return AddressBuilder.getInstance().
                withStreet(input.logradouro())
                .withNumber(input.numero())
                .withNeighborhood(input.bairro())
                .withCity(input.cidade())
                .withState(input.estado())
                .withComplement(input.complemento())
                .withZipCode(input.cep()).build();

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
                restaurant.getAddress().getCidade(),
                restaurant.getAddress().getCep(),
                restaurant.getAddress().getEstado()
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
