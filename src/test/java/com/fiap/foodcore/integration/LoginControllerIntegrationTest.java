package com.fiap.foodcore.integration;

import com.fiap.foodcore.application.usecase.interactor.CreateUserInteractor;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.LoginRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class LoginControllerIntegrationTest {

    @Autowired
    private CreateUserInteractor createUserInteractor;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }


    @Test
    void shouldOwnerLoginSuccessfully() {
        UserCreateRequestDTO createUser = UserTestHelper.createValidGenericOwnerRequest();

        CreateUserInput input = UserPresenter.toInputCreate(createUser);
        CreateUserOutput output = createUserInteractor.execute(input);

        LoginRequestDTO loginRequest = new LoginRequestDTO(createUser.login(), createUser.senha());

        given()
                .body(loginRequest)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldCustomerLoginSuccessfully() {
        UserCreateRequestDTO createUser = UserTestHelper.createValidGenericCustomerRequest();

        CreateUserInput input = UserPresenter.toInputCreate(createUser);
        CreateUserOutput output = createUserInteractor.execute(input);

        LoginRequestDTO loginRequest = new LoginRequestDTO(createUser.login(), createUser.senha());

        given()
                .body(loginRequest)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldFailLoginWithInvalidCredentials() {
        UserCreateRequestDTO createUser = UserTestHelper.createValidGenericUserRequest();

        CreateUserInput input = UserPresenter.toInputCreate(createUser);
        CreateUserOutput output = createUserInteractor.execute(input);

        LoginRequestDTO loginRequest = new LoginRequestDTO(createUser.login(), "senhaErrada");

        given()
                .body(loginRequest)
                .when()
                .post("/login")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
