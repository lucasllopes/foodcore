package com.fiap.foodcore.domain.builder;

import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.domain.Restaurant;

import java.time.LocalTime;

public class RestaurantBuilder {
    private Long id;
    private String name;
    private Address address;
    private String cuisineType;
    private LocalTime openingHours;
    private LocalTime closingHours;
    private Long ownerId;

    public static RestaurantBuilder getInstance() {
        return new RestaurantBuilder();
    }

    public RestaurantBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public RestaurantBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RestaurantBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public RestaurantBuilder withCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
        return this;
    }

    public RestaurantBuilder withOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
        return this;
    }

    public RestaurantBuilder withClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
        return this;
    }

    public RestaurantBuilder withOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public Restaurant build() {
        return Restaurant.builder()
                .id(id)
                .name(name)
                .address(address)
                .cuisineType(cuisineType)
                .openingHours(openingHours)
                .closingHours(closingHours)
                .ownerId(ownerId)
                .build();
    }
}
