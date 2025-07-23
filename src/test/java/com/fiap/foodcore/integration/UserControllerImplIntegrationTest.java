package com.fiap.foodcore.integration;


import com.fiap.foodcore.application.usecase.CreateUserUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
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

import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserControllerImplIntegrationTest {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    
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
    void shouldCreateOwnerUserSuccessfully() {
        UserCreateRequestDTO dto = UserTestHelper.createValidGenericOwnerRequest();

        given()
                .body(dto)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CREATED.value())
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
        UserCreateRequestDTO dto = UserTestHelper.createValidGenericCustomerRequest();

        given()
                .body(dto)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CREATED.value())
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
        UserCreateRequestDTO owner = UserTestHelper.createValidOwnerUserToUpdateRequest();

        UserResponseDTO response = createUser(owner);

        String token = authenticateAndGetToken(owner);

        UserUpdateRequestDTO updateUser = UserTestHelper.createValidOwnerUserUpdateRequest();

        given()
                .header("Authorization", "Bearer " + token)
                .body(updateUser)
                .when()
                .put("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("email"))
                .body("$", hasKey("enderecos"))
                .body("nome", equalTo(updateUser.nome()))
                .body("email", equalTo(updateUser.email()));

    }

    @Test
    void shouldUpdateCustomerUserSuccessfully() {
        UserCreateRequestDTO customer = UserTestHelper.createValidCustomerUserToUpdateRequest();

        UserResponseDTO response = createUser(customer);

        String token = authenticateAndGetToken(customer);

        UserUpdateRequestDTO updateUser = UserTestHelper.createValidCustomerUserUpdateRequest();

        given()
                .header("Authorization", "Bearer " + token)
                .body(updateUser)
                .when()
                .put("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("email"))
                .body("$", hasKey("enderecos"))
                .body("nome", equalTo(updateUser.nome()))
                .body("email", equalTo(updateUser.email()));

    }



    @Test
    void shouldDeleteOwnerUserSuccessfully() {
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();

        UserResponseDTO response = createUser(owner);

        String token = authenticateAndGetToken(owner);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    void shouldDeleteCustomerUserSuccessfully() {
        UserCreateRequestDTO customer = UserTestHelper.createValidGenericCustomerRequest();

        UserResponseDTO response = createUser(customer);

        String token = authenticateAndGetToken(customer);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/usuarios/{id}", response.id())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    void shouldListUsersSuccessfully(){
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();

        UserResponseDTO ownerResponse = createUser(owner);

        UserCreateRequestDTO customer = UserTestHelper.createValidGenericCustomerRequest();

        UserResponseDTO customerResponse = createUser(customer);

        String token = authenticateAndGetToken(owner);

        String response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/usuarios?page=0&size=50")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", notNullValue())
                .body("content.size()", greaterThan(0))
                .body("content.login", hasItems(customerResponse.login(), ownerResponse.login()))
                .body("content.nome", hasItems(customerResponse.nome(), ownerResponse.nome()))
                .body("totalElements", greaterThan(0)).extract().asPrettyString();

    }

    @Test
    void shouldFindUserByIdSuccessfully(){
        UserCreateRequestDTO createOwnerUser = UserTestHelper.createValidGenericOwnerRequest();

        UserResponseDTO responseOwner = createUser(createOwnerUser);

        UserCreateRequestDTO createCustomerUser = UserTestHelper.createValidGenericCustomerRequest();

        UserResponseDTO responseCustumer = createUser(createCustomerUser);

        String token = authenticateAndGetToken(createOwnerUser);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/usuarios/{id}", responseCustumer.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue())
                .body("login", equalTo(responseCustumer.login()))
                .body("nome", equalTo(responseCustumer.nome()));
    }


    @Test
    void shouldChangePasswordSuccessfully(){
        UserCreateRequestDTO createOwnerUser = UserTestHelper.createValidGenericOwnerRequest();

        UserResponseDTO response = createUser(createOwnerUser);

        String token = authenticateAndGetToken(createOwnerUser);

        ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO(createOwnerUser.senha(), "novasenha");

        given()
                .header("Authorization", "Bearer " + token)
                .body(changePasswordRequestDTO)
                .when()
                .put("/usuarios/{id}/senha", response.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("Senha atualizada com sucesso."));
    }


    @Test
    void shouldFailNoAuthenticate(){
        UserCreateRequestDTO createOwnerUser = UserTestHelper.createValidGenericOwnerRequest();

        UserResponseDTO response = createUser(createOwnerUser);

        ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO(createOwnerUser.senha(), "novasenha");

        given()
                .body(changePasswordRequestDTO)
                .when()
                .put("/usuarios/{id}/senha", response.id())
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void shouldFailInvalidateJwtToken(){
        UserCreateRequestDTO createOwnerUser = UserTestHelper.createValidGenericOwnerRequest();

        UserResponseDTO response = createUser(createOwnerUser);

        ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO(createOwnerUser.senha(), "novasenha");

        given()
                .header("Authorization", "Bearer invalid_token")
                .body(changePasswordRequestDTO)
                .when()
                .put("/usuarios/{id}/senha", response.id())
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        CreateUserInput inputOwner = UserPresenter.toInputCreate(dto);
        CreateUserOutput outputOwner = createUserUseCase.execute(inputOwner);
        return UserPresenter.toDto(outputOwner);
    }

    @Test
    void shouldAssignUserTypeToUserSuccessfully() {

        UserCreateRequestDTO userCustomerDTO = UserTestHelper.createValidGenericCustomerRequest();
        UserResponseDTO userCustomerResponse = createUser(userCustomerDTO);

        UserCreateRequestDTO userOwnerDTO = UserTestHelper.createValidGenericOwnerRequest();
        UserResponseDTO userOwnerResponse = createUser(userOwnerDTO);

        UserTypeRequestDTO userTypeRequestDTO = new UserTypeRequestDTO("USER TYPE TEST");

        String adminToken = authenticateAndGetToken(userOwnerDTO);

        Long createdTypeId =
                given()
                        .header("Authorization", "Bearer " + adminToken)
                        .body(userTypeRequestDTO)
                        .when()
                        .post("/tipos")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .jsonPath()
                        .getLong("id");

        // Act
        given()
                .header("Authorization", "Bearer " + adminToken)
                .body(String.format("{\"idUserType\": %d}", createdTypeId))
                .when()
                .put("/usuarios/{id}/tipo", userCustomerResponse.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(userCustomerResponse.id().intValue()))
                .body("tipoUsuario", notNullValue())
                .body("tipoUsuario.id", equalTo(createdTypeId.intValue()))
                .body("tipoUsuario.name", equalTo(userTypeRequestDTO.name()));
    }
}
