package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.restaurant.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.restaurant.CreateRestaurantInput;
import lombok.Getter;

import java.time.LocalTime;


@Getter
public class Restaurant {

    private Long id;
    private String name;
    private Address address;
    private String cuisineType;
    private LocalTime openingHours;
    private LocalTime closingHours;
    private Long ownerId;

    private Restaurant() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public void updateInformation(String name, String cuisineType,
                                  LocalTime openingHours, LocalTime closingHours,
                                  Address address) {
        this.name = name;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.address = address;
    }

    public static class Builder {
        private final Restaurant restaurant;

        private Builder() {
            restaurant = new Restaurant();
        }

        public Builder id(Long id) {
            restaurant.id = id;
            return this;
        }

        public Builder name(String name) {
            restaurant.name = name;
            return this;
        }

        public Builder address(Address address) {
            restaurant.address = address;
            return this;
        }

        public Builder cuisineType(String cuisineType) {
            restaurant.cuisineType = cuisineType;
            return this;
        }

        public Builder openingHours(LocalTime openingHours) {
            restaurant.openingHours = openingHours;
            return this;
        }

        public Builder closingHours(LocalTime closingHours) {
            restaurant.closingHours = closingHours;
            return this;
        }

        public Builder ownerId(Long ownerId) {
            restaurant.ownerId = ownerId;
            return this;
        }

        public Restaurant build() {
            return restaurant;
        }
    }
}