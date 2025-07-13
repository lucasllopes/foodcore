package com.fiap.foodcore.integration;


import com.fiap.foodcore.application.usecase.CreateUserInteractor;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.LoginRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserUpdateRequestDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class UserControllerIntegrationTest {

    @Autowired
    private CreateUserInteractor createUserInteractor;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldCreateOwnerUserSuccessfully() {
        UserCreateRequestDTO dto = UserTestHelper.createValidOwnerUserRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("email"))
                .body("$", hasKey("login"))
                .body("$", hasKey("tipo"))
                .body("$", hasKey("enderecos"))
                .body("nome", equalTo(dto.nome()))
                .body("email", equalTo(dto.email()))
                .body("login", equalTo(dto.login()))
                .body("tipo", equalTo(dto.tipo()));

    }

    @Test
    void shouldCreateCustomerUserSuccessfully() {
        UserCreateRequestDTO dto = UserTestHelper.createValidCustomerUserRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("email"))
                .body("$", hasKey("login"))
                .body("$", hasKey("tipo"))
                .body("$", hasKey("enderecos"))
                .body("nome", equalTo(dto.nome()))
                .body("email", equalTo(dto.email()))
                .body("login", equalTo(dto.login()))
                .body("tipo", equalTo(dto.tipo()));

    }

    @Test
    void shouldUpdateOwnerUserSuccessfully() {
        UserCreateRequestDTO createUser = UserTestHelper.createValidOwnerUserToUpdateRequest();

        CreateUserInput input = UserPresenter.toInputCreate(createUser);
        CreateUserOutput output = createUserInteractor.execute(input);

        UserResponseDTO response = UserPresenter.toDto(output);
        
        LoginRequestDTO loginRequest = new LoginRequestDTO(createUser.login(), createUser.senha());

        String token = getToken(loginRequest);

        UserUpdateRequestDTO updateUser = UserTestHelper.createValidOwnerUserUpdateRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(updateUser)
                .when()
                .put("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("email"))
                .body("$", hasKey("enderecos"))
                .body("nome", equalTo(updateUser.nome()))
                .body("email", equalTo(updateUser.email()));

    }

    @Test
    void shouldUpdateCustomerUserSuccessfully() {
        UserCreateRequestDTO createUser = UserTestHelper.createValidCustomerUserToUpdateRequest();

        CreateUserInput input = UserPresenter.toInputCreate(createUser);
        CreateUserOutput output = createUserInteractor.execute(input);

        UserResponseDTO response = UserPresenter.toDto(output);

        LoginRequestDTO loginRequest = new LoginRequestDTO(createUser.login(), createUser.senha());

        String token = getToken(loginRequest);

        UserUpdateRequestDTO updateUser = UserTestHelper.createValidCustomerUserUpdateRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(updateUser)
                .when()
                .put("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("email"))
                .body("$", hasKey("enderecos"))
                .body("nome", equalTo(updateUser.nome()))
                .body("email", equalTo(updateUser.email()));

    }



    @Test
    void shouldDeleteOwnerUserSuccessfully() {
        UserCreateRequestDTO createUser = UserTestHelper.createValidGenericUserRequest();

        CreateUserInput input = UserPresenter.toInputCreate(createUser);
        CreateUserOutput output = createUserInteractor.execute(input);

        UserResponseDTO response = UserPresenter.toDto(output);

        LoginRequestDTO loginRequest = new LoginRequestDTO(createUser.login(), createUser.senha());

        String token = getToken(loginRequest);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    void shouldDeleteCustomerUserSuccessfully() {
        UserCreateRequestDTO createUser = UserTestHelper.createValidGenericUserRequest();

        CreateUserInput input = UserPresenter.toInputCreate(createUser);
        CreateUserOutput output = createUserInteractor.execute(input);

        UserResponseDTO response = UserPresenter.toDto(output);

        LoginRequestDTO loginRequest = new LoginRequestDTO(createUser.login(), createUser.senha());

        String token = getToken(loginRequest);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    private static String getToken(LoginRequestDTO loginRequest) {
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
