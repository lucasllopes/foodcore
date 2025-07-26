

package com.fiap.foodcore.integration;

import com.fiap.foodcore.application.usecase.CreateUserUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserSubtypeRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserSubtypeUpdateRequestDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserSubtypeControllerImplIntegrationTest {

    @Autowired
    private CreateUserUseCase useCase;

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
    void shouldCreateUserSubtypeSuccessfully() {

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserSubtypeRequestDTO requestDTO = new UserSubtypeRequestDTO("TESTE");

        given()
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("name", equalTo(requestDTO.name().toUpperCase()));
    }

    @Test
    void shouldFailCreateUserSubtypeSameName() {

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserSubtypeRequestDTO requestDTO = new UserSubtypeRequestDTO("TESTE DUPLICADO");

        given()
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("name", equalTo(requestDTO.name().toUpperCase()));

        given()
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void shouldGetUserSubtypeByIdSuccessfully() {
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = UserTestHelper.authenticateAndGetToken(owner);

        UserSubtypeRequestDTO requestDTO = new UserSubtypeRequestDTO("OWNER");

        var createdTypeId =
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(requestDTO)
                        .when()
                        .post("/tipos")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .jsonPath().getInt("id");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/tipos/" + createdTypeId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(createdTypeId))
                .body("name", equalTo("OWNER"));
    }

    @Test
    void shouldGetUserSubtypeByNameSuccessfully() {
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserSubtypeRequestDTO requestDTO = new UserSubtypeRequestDTO("GENERIC TYPE");

        given()
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/tipos/name/GENERIC TYPE")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("[0].name", equalTo("GENERIC TYPE"));
    }

    @Test
    void shouldUpdateUserSubtypeSuccessfully() {
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserSubtypeRequestDTO createDTO = new UserSubtypeRequestDTO("GERENTE");

        Long createdTypeId =
                given()
                        .header("Authorization", "Bearer " + token)
                        .body(createDTO)
                        .when()
                        .post("/tipos")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .jsonPath()
                        .getLong("id");

        UserSubtypeUpdateRequestDTO updateDTO = new UserSubtypeUpdateRequestDTO("GERENTE ATUALIZADO");

        given()
                .header("Authorization", "Bearer " + token)
                .body(updateDTO)
                .when()
                .put("/tipos/{id}", createdTypeId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(createdTypeId.intValue()))
                .body("name", equalTo(updateDTO.name()));
    }

    @Test
    void shouldDeleteUserSubtypeSuccessfully() {

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserSubtypeRequestDTO requestDTO = new UserSubtypeRequestDTO("GENERIC_TYPE");

        Long createdSubtypeId = given()
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .jsonPath()
                .getLong("id");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/tipos/{id}", createdSubtypeId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void shouldFailDeleteUserSubtypeNotFoundId() {

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserSubtypeRequestDTO requestDTO = new UserSubtypeRequestDTO("GENERIC_TYPE_FAIL");

        given()
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .jsonPath()
                .getLong("id");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/tipos/{id}", 99999999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void shouldListUserSubtypePaginatedSuccessfully() {

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserSubtypeRequestDTO requestDTO = new UserSubtypeRequestDTO("Generic User" + UUID.randomUUID());

        given()
                .header("Authorization", "Bearer " + token)
                .body(requestDTO)
                .when()
                .post("/tipos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("name", equalTo(requestDTO.name().toUpperCase()));


        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/tipos?page=0&size=50&sort=name,asc")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", notNullValue())
                .body("content.size()", greaterThan(0))
                .body("content.name", hasItems(requestDTO.name().toUpperCase()))
                .body("totalElements", greaterThanOrEqualTo(1));
    }

    public void createUser(UserCreateRequestDTO dto) {
        CreateUserInput inputOwner = UserPresenter.toInputCreate(dto);
        CreateUserOutput outputOwner = useCase.execute(inputOwner);
        UserPresenter.toDto(outputOwner);
    }
}
