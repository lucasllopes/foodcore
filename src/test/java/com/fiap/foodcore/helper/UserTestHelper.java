package com.fiap.foodcore.helper;

import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UserTestHelper {

    public static User getUserWithDefaultId() {

        Long id = 1L;
        return User.rebuildUser(id, "User", "user@email.com", "user", "password",
                UserTypeDomain.DONO, List.of(), null);

    }

    public static User getUserWithIdParametrized(Long id) {

        return User.rebuildUser(id, "User", "user@email.com", "user", "password",
                UserTypeDomain.DONO, List.of(), null);

    }

    public static AddressCreateRequestDTO createValidAddressRequest(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            String cep) {
        return new AddressCreateRequestDTO(logradouro, numero, complemento, bairro, cidade, estado, cep);
    }

    public static AddressUpdateRequestDTO createValidAddressUpdateRequest(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            String cep) {
        return new AddressUpdateRequestDTO(logradouro, numero, complemento, bairro, cidade, estado, cep);
    }

    public static UserCreateRequestDTO createValidOwnerUserToUpdateRequest() {
        return new UserCreateRequestDTO("Owner Old",
                "owner_old@email.com",
                "ownerneedupdate",
                "password",
                "DONO",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));
    }

    public static UserCreateRequestDTO createValidOwnerUserToCreateRestaurantRequest() {
        return new UserCreateRequestDTO("Owner Old",
                "owner_restaurant@email.com",
                "ownerrestaurant",
                "password",
                "DONO",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));
    }


    public static UserCreateRequestDTO createValidCustomerUserToUpdateRequest() {
        return new UserCreateRequestDTO("Customer Old",
                "customer_old@email.com",
                "customerneedupdate",
                "password",
                "CLIENTE",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));
    }

    public static UserUpdateRequestDTO createValidOwnerUserUpdateRequest() {
        return new UserUpdateRequestDTO("Owner Updated",
                "owner_updated@email.com",
                List.of(
                        createValidAddressUpdateRequest("Rua das Flores Atualizada", "789", null, "Flamengo", "Rio de Janeiro", "RJ", "24216-902"),
                        createValidAddressUpdateRequest("Av. Brasil Atualizada", "555", "354", "Botafogo", "Rio de Janeiro", "RJ", "45632-985")
                ));
    }

    public static UserUpdateRequestDTO createValidCustomerUserUpdateRequest() {
        return new UserUpdateRequestDTO("Customer Updated",
                "customer_updated@email.com",
                List.of(
                        createValidAddressUpdateRequest("Rua das Flores Atualizada", "789", null, "Flamengo", "Rio de Janeiro", "RJ", "24216-902"),
                        createValidAddressUpdateRequest("Av. Brasil Atualizada", "555", "354", "Botafogo", "Rio de Janeiro", "RJ", "45632-985")
                ));
    }

    public static UserCreateRequestDTO createValidGenericOwnerRequest() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return new UserCreateRequestDTO("Generic Owner",
                "generic_owner"+suffix+"@email.com",
                "genericowner"+suffix,
                "password",
                "DONO",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));
    }

    public static UserCreateRequestDTO createValidGenericCustomerRequest() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return new UserCreateRequestDTO("Generic Customer",
                "generic_customer"+suffix+"@email.com",
                "genericcustomer"+suffix,
                "password",
                "CLIENTE",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));
    }

    public static UserCreateRequestDTO createValidGenericUserRequest() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return new UserCreateRequestDTO("Generic User",
                "generic_user"+suffix+"@email.com",
                "genericuser"+suffix,
                "password",
                "CLIENTE",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));
    }

    public static String authenticateAndGetToken(UserCreateRequestDTO dto) {
        LoginRequestDTO loginRequest = new LoginRequestDTO(dto.login(), dto.senha());
        return given()
                .body(loginRequest)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

    }


    /* TODO Remover esse metodo e utilizar o authenticateAndGetToken" */
    public static String getToken(LoginRequestDTO loginRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();

    }
}
