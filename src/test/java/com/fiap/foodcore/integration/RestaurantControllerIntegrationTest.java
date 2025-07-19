package com.fiap.foodcore.integration;

import com.fiap.foodcore.application.usecase.interactor.user.CreateUserInteractor;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.RestaurantCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static com.fiap.foodcore.helper.RestaurantTestHelper.createValidRestarantRequest;
import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class RestaurantControllerIntegrationTest {

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
    void shouldCreateRestaurant(){

        UserCreateRequestDTO owner = UserTestHelper.createValidOwnerUserToCreateRestaurantRequest();

        UserResponseDTO response = createUser(owner);

        String token = authenticateAndGetToken(owner);

        RestaurantCreateRequestDTO dto = createValidRestarantRequest(response.id());

        given()
                .header("Authorization", "Bearer " + token)
                .body(dto)
                .when()
                .post("/restaurantes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("cuisineType"))
                .body("$", hasKey("openingHours"))
                .body("$", hasKey("closingHours"))
                .body("$", hasKey("ownerId"))
                .body("nome", equalTo(dto.nome()))
                .body("cuisineType", equalTo(dto.cuisineType()))
                .body("openingHours", equalTo(dto.openingHours().toString()))
                .body("closingHours", equalTo(dto.closingHours().toString()))
                .body("ownerId", equalTo(dto.ownerId().intValue()));

    }


    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        CreateUserInput inputOwner = UserPresenter.toInputCreate(dto);
        CreateUserOutput outputOwner = createUserInteractor.execute(inputOwner);
        return UserPresenter.toDto(outputOwner);
    }


}
