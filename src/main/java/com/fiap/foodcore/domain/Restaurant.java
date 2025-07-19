package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Restaurant {

    private Long id;
    private String name;
    private Address address;
    private String cuisineType;
    private LocalTime openingHours;
    private LocalTime closingHours;
    private Long ownerId;

    private Restaurant(){

    }

    public static Restaurant create(CreateRestaurantInput input){
        Restaurant restaurant = new Restaurant();
        restaurant.name = input.name();
        if (input.address() != null) {
            restaurant.address = Address.addAddress(input.address());
        }
        restaurant.cuisineType = input.cuisineType();
        restaurant.openingHours = input.openingHours();
        restaurant.closingHours = input.closingHours();
        restaurant.ownerId = input.ownerId();
        return restaurant;
    }

    public static Restaurant create(
            Long id,
            String name,
            Address address,
            String cuisineType,
            LocalTime openingHours,
            LocalTime closingHours,
            Long ownerId
    ) {
        Restaurant restaurant = new Restaurant();
        restaurant.id = id;
        restaurant.name = name;
        restaurant.address = address;
        restaurant.cuisineType = cuisineType;
        restaurant.openingHours = openingHours;
        restaurant.closingHours = closingHours;
        restaurant.ownerId = ownerId;
        return restaurant;
    }
    public void updateInformation(UpdateRestaurantInput input) {
        this.name = input.nome();
        this.cuisineType = input.cuisineType();
        this.openingHours = input.openingHours();
        this.closingHours = input.closingHours();
        this.address = Address.addAddress(input.enderecos());
    }
}
