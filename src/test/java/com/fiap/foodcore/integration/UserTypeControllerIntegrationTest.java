

package com.fiap.foodcore.integration;

import com.fiap.foodcore.application.usecase.CreateUserUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserTypeRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserTypeUpdateRequestDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class UserTypeControllerIntegrationTest {

    @Autowired
    private CreateUserUseCase useCase;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldCreateOwnerTypeSuccessfully() {

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO("Generic User" + UUID.randomUUID());

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("name", equalTo(requestDTO.name().toUpperCase()));
    }

    @Test
    void shouldGetUserTypeByIdSuccessfully() {
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = UserTestHelper.authenticateAndGetToken(owner);

        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO("OWNER");

        var createdTypeId =
                given()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + token)
                        .body(requestDTO)
                        .when()
                        .post("/tipos")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .jsonPath().getInt("id");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/tipos/" + createdTypeId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(createdTypeId))
                .body("name", equalTo("OWNER"));
    }

    @Test
    void shouldGetUserTypeByNameSuccessfully() {
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO("GENERIC TYPE");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/tipos/name/GENERIC TYPE")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("[0].name", equalTo("GENERIC TYPE"));
    }

    @Test
    void shouldUpdateUserTypeSuccessfully() {
        // Arrange
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserTypeRequestDTO createDTO = new UserTypeRequestDTO("GERENTE");

        Long createdTypeId =
                given()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + token)
                        .body(createDTO)
                        .when()
                        .post("/tipos")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .jsonPath()
                        .getLong("id");

        UserTypeUpdateRequestDTO updateDTO = new UserTypeUpdateRequestDTO("ADMIN");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(updateDTO)
                .when()
                .put("/tipos/" + createdTypeId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(createdTypeId.intValue()))
                .body("name", equalTo(updateDTO.name()));
    }

    public void createUser(UserCreateRequestDTO dto) {
        CreateUserInput inputOwner = UserPresenter.toInputCreate(dto);
        CreateUserOutput outputOwner = useCase.execute(inputOwner);
        UserPresenter.toDto(outputOwner);
    }
}
