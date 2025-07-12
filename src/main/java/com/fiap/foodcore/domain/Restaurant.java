package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import lombok.Getter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Restaurant {
    private long id;
    private String name;
    private List<Address> address;
    private String cuisineType;
    private String openingHours;
    private String closingHours;
    private Long ownerId;
    private Restaurant(){

    }
    public static Restaurant create(CreateRestaurantInput input){
        Restaurant restaurant = new Restaurant();
        restaurant.name = input.name();
        if (!input.address().isEmpty()) {
            restaurant.address = input.address()
                    .stream()
                    .map(Address::addAddress)
                    .toList();
        }
        restaurant.cuisineType = input.cuisineType();
        restaurant.openingHours = input.openingHours();
        restaurant.closingHours = input.closingHours();
        restaurant.ownerId = input.ownerId();
        return restaurant;
    }
}
