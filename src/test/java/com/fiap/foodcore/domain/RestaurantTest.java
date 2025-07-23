package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import com.fiap.foodcore.application.usecase.input.restaurant.CreateRestaurantInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;

@ActiveProfiles("test")
class RestaurantTest {

    @Test
    public void shouldCreateRestaurant(){
        Restaurant restaurantAtual = Restaurant.create(getCreateRestaurantInput());

        Assertions.assertEquals("Restaurante XPTO", restaurantAtual.getName());
        assertAddress(restaurantAtual.getAddress());
        Assertions.assertEquals("Fast Food", restaurantAtual.getCuisineType());
        Assertions.assertEquals("Restaurante XPTO", restaurantAtual.getName());
        Assertions.assertEquals(LocalTime.of(19,00), restaurantAtual.getOpeningHours());
        Assertions.assertEquals(LocalTime.of(23,59), restaurantAtual.getClosingHours());
        Assertions.assertEquals(2L, restaurantAtual.getOwnerId());

    }
    private void assertAddress(Address address){
        Assertions.assertEquals("Rua A", address.getLogradouro());
        Assertions.assertEquals("123", address.getNumero());
        Assertions.assertEquals("", address.getComplemento());
        Assertions.assertEquals("Bairro", address.getBairro());
        Assertions.assertEquals("Cidade", address.getCidade());
        Assertions.assertEquals("12345-678", address.getCep());
        Assertions.assertEquals("SP", address.getEstado());
    }
    private CreateRestaurantInput getCreateRestaurantInput(){
        LocalTime openingHours = LocalTime.of(19,00);
        LocalTime closingHours = LocalTime.of(23,59);
        CreateRestaurantInput createRestaurantInput = new CreateRestaurantInput("Restaurante XPTO", getCreateAddressInput(), "Fast Food", openingHours, closingHours, 2L);
        return createRestaurantInput;
    }
    private CreateAddressInput getCreateAddressInput(){
        return new CreateAddressInput("Rua A", "123", "", "Bairro", "Cidade", "12345-678", "SP");
    }
}