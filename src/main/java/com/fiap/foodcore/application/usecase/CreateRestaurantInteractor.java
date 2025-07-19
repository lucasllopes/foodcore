package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.application.gateway.RestaurantGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.mapper.RestaurantMapper;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.domain.exception.OwnerRestaurantException;

public class CreateRestaurantInteractor {

    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public CreateRestaurantInteractor(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public CreateRestaurantOutput execute(CreateRestaurantInput createRestaurantInput) {
        Restaurant restaurant = RestaurantMapper.toDomain(createRestaurantInput);
        this.validateCreateRestaurant(restaurant);
        Restaurant savedRestaurant = this.restaurantGateway.save(restaurant);
        return RestaurantMapper.fromDomain(savedRestaurant);
    }

    private void validateCreateRestaurant(Restaurant restaurant) {
        validateOwner(restaurant.getOwnerId());
        validateRestaurant(restaurant);
    }
    private void validateRestaurant(Restaurant restaurant){
        this.restaurantGateway.findByName(restaurant.getName()).ifPresent(existingRestaurant -> {throw new DuplicatedDataException("Já existe um restaurante cadastrado com este nome.");});

    }

    private void validateOwner(Long ownerId) {
        User owner = this.userGateway.findById(ownerId).orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
        if (!validateUserIsOwner(owner.getTipo())){
            throw new OwnerRestaurantException("O código de usuário informado não é do tipo Dono.");
        }
    }

    private boolean validateUserIsOwner(UserTypeDomain userTypeDomain){
        return UserTypeDomain.isOwner(userTypeDomain);
    }
}
