

package com.fiap.foodcore.integration;

import com.fiap.foodcore.application.usecase.interactor.CreateUserInteractor;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserTypeRequestDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class UserTypeControllerIntegrationTest {

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
    void shouldCreateOwnerTypeSuccessfully() {

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        createUser(owner);
        String token = authenticateAndGetToken(owner);

        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO("DONO");

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
                .body("name", equalTo(requestDTO.name()));
    }

    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        CreateUserInput inputOwner = UserPresenter.toInputCreate(dto);
        CreateUserOutput outputOwner = createUserInteractor.execute(inputOwner);
        return UserPresenter.toDto(outputOwner);
    }

}
