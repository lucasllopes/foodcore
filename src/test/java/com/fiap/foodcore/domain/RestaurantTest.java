package com.fiap.foodcore.domain;

import com.fiap.foodcore.domain.builder.AddressBuilder;
import com.fiap.foodcore.domain.builder.RestaurantBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;

@ActiveProfiles("test")
class RestaurantTest {

    @Test
    public void shouldCreateRestaurant(){
        Restaurant restaurantAtual = getCreateRestaurantInput();

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
    private Restaurant getCreateRestaurantInput(){
        LocalTime openingHours = LocalTime.of(19,00);
        LocalTime closingHours = LocalTime.of(23,59);
        return RestaurantBuilder.getInstance().
                withName("Restaurante XPTO")
                .withAddress(getCreateAddressInput())
                .withCuisineType("Fast Food")
                .withOpeningHours(openingHours)
                .withClosingHours(closingHours)
                .withOwnerId(2L).build();
    }
    private Address getCreateAddressInput(){
        return AddressBuilder.getInstance()
                .withStreet("Rua A")
                .withNumber("123")
                .withNeighborhood("Bairro")
                .withCity("Cidade")
                .withState("SP")
                .withComplement("")
                .withZipCode("12345-678").build();
    }
}