package com.fiap.foodcore.helper;

import com.fiap.foodcore.infrastructure.web.controller.dto.AddressCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.RestaurantCreateRequestDTO;

import java.time.LocalTime;
import java.util.List;

public class RestaurantTestHelper {


    public static RestaurantCreateRequestDTO createValidRestarantRequest(Long ownerId){
        return new RestaurantCreateRequestDTO(
                "Van Been Tap House",
                "Cervejaria",
                List.of(
                        createValidAddressRequest("R. Joaquim Távora", "1039", null, "Vila Mariana", "São Paulo", "SP", "04015-002")
                ),
                LocalTime.of(10, 30),
                LocalTime.of(22, 00),
                ownerId
        );
    }

    private static AddressCreateRequestDTO createValidAddressRequest(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            String cep) {
        return new AddressCreateRequestDTO(logradouro, numero, complemento, bairro, cidade, estado, cep);
    }
}
