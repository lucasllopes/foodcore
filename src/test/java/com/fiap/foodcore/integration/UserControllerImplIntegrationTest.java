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

import java.util.List;
import java.util.UUID;

import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static com.fiap.foodcore.helper.UserTestHelper.createValidAddressRequest;
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
    void shouldFailCreateOwnerUserSameEmail() {
        UserCreateRequestDTO original = UserTestHelper.createValidGenericOwnerRequest();

        given()
                .body(original)
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
                .body("nome", equalTo(original.nome()))
                .body("email", equalTo(original.email()))
                .body("login", equalTo(original.login()))
                .body("tipo", equalTo(original.tipo()));


        given()
                .body(original)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void shouldFailCreateOwnerUserSameLogin() {
        UserCreateRequestDTO original = UserTestHelper.createValidGenericOwnerRequest();

        given()
                .body(original)
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
                .body("nome", equalTo(original.nome()))
                .body("email", equalTo(original.email()))
                .body("login", equalTo(original.login()))
                .body("tipo", equalTo(original.tipo()));

        String suffix = UUID.randomUUID().toString().substring(0, 8);
        UserCreateRequestDTO invalidLogin = new UserCreateRequestDTO("Generic Owner",
                "generic_owner"+suffix+"@email.com",
                original.login(),
                "password",
                "DONO",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));

        given()
                .body(invalidLogin)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void shouldCreateEmployeeUserSuccessfully() {
        UserCreateRequestDTO dto = UserTestHelper.createValidGenericEmployeeRequest();

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
    void shouldFailCreateEmployeeUserSameEmail() {
        UserCreateRequestDTO original = UserTestHelper.createValidGenericEmployeeRequest();

        given()
                .body(original)
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
                .body("nome", equalTo(original.nome()))
                .body("email", equalTo(original.email()))
                .body("login", equalTo(original.login()))
                .body("tipo", equalTo(original.tipo()));


        given()
                .body(original)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void shouldFailCreateEmployeeUserSameLogin() {
        UserCreateRequestDTO original = UserTestHelper.createValidGenericEmployeeRequest();

        given()
                .body(original)
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
                .body("nome", equalTo(original.nome()))
                .body("email", equalTo(original.email()))
                .body("login", equalTo(original.login()))
                .body("tipo", equalTo(original.tipo()));

        String suffix = UUID.randomUUID().toString().substring(0, 8);
        UserCreateRequestDTO invalidLogin = new UserCreateRequestDTO("Generic Owner",
                "generic_employee"+suffix+"@email.com",
                original.login(),
                "password",
                "COLABORADOR",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));

        given()
                .body(invalidLogin)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
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
    void shouldFailCreateCustomerUserSameEmail() {
        UserCreateRequestDTO original = UserTestHelper.createValidGenericCustomerRequest();

        given()
                .body(original)
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
                .body("nome", equalTo(original.nome()))
                .body("email", equalTo(original.email()))
                .body("login", equalTo(original.login()))
                .body("tipo", equalTo(original.tipo()));


        given()
                .body(original)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void shouldFailCreateCustomerUserSameLogin() {
        UserCreateRequestDTO original = UserTestHelper.createValidGenericOwnerRequest();

        given()
                .body(original)
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
                .body("nome", equalTo(original.nome()))
                .body("email", equalTo(original.email()))
                .body("login", equalTo(original.login()))
                .body("tipo", equalTo(original.tipo()));

        String suffix = UUID.randomUUID().toString().substring(0, 8);
        UserCreateRequestDTO invalidLogin = new UserCreateRequestDTO("Generic Owner",
                "generic_customer"+suffix+"@email.com",
                original.login(),
                "password",
                "CLIENTE",
                List.of(
                        createValidAddressRequest("Rua das Flores", "123", "APTO 123", "Centro", "São Paulo", "SP", "01234-567"),
                        createValidAddressRequest("Av. Brasil", "456", null, "Jardins", "São Paulo", "SP", "12345-678")
                ));

        given()
                .body(invalidLogin)
                .when()
                .post("/usuarios")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
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
    void shouldUpdateEmployeeUserSuccessfully() {
        UserCreateRequestDTO emloyee = UserTestHelper.createValidEmployeeUserToUpdateRequest();

        UserResponseDTO response = createUser(emloyee);

        String token = authenticateAndGetToken(emloyee);

        UserUpdateRequestDTO updateUser = UserTestHelper.createValidEmployeeUserUpdateRequest();

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
    void shouldDeleteEmployeeUserSuccessfully() {
        UserCreateRequestDTO employee = UserTestHelper.createValidGenericEmployeeRequest();

        UserResponseDTO response = createUser(employee);

        String token = authenticateAndGetToken(employee);

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

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/usuarios?page=0&size=50&sort=nome,asc")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", notNullValue())
                .body("content.size()", greaterThan(0))
                .body("content.login", hasItems(customerResponse.login(), ownerResponse.login()))
                .body("content.nome", hasItems(customerResponse.nome(), ownerResponse.nome()))
                .body("totalElements", greaterThan(0));

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


    @Test
    void shouldAssignUserSubtypeToUserSuccessfully() {

        UserCreateRequestDTO userCustomerDTO = UserTestHelper.createValidGenericCustomerRequest();
        UserResponseDTO userCustomerResponse = createUser(userCustomerDTO);

        UserCreateRequestDTO userOwnerDTO = UserTestHelper.createValidGenericOwnerRequest();
        createUser(userOwnerDTO);

        UserSubtypeRequestDTO userSubtypeRequestDTO = new UserSubtypeRequestDTO("USER TYPE TEST");

        String adminToken = authenticateAndGetToken(userOwnerDTO);

        Long createdSubtypeId =
                given()
                        .header("Authorization", "Bearer " + adminToken)
                        .body(userSubtypeRequestDTO)
                        .when()
                        .post("/subtipos")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .jsonPath()
                        .getLong("id");


        AssignUserSubtypeToUserDTO dto = new AssignUserSubtypeToUserDTO(createdSubtypeId);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .body(dto)
                .when()
                .put("/usuarios/{id}/subtipo", userCustomerResponse.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(userCustomerResponse.id().intValue()))
                .body("subtipoUsuario", notNullValue())
                .body("subtipoUsuario.name", equalTo(userSubtypeRequestDTO.name()));
    }


    @Test
    void shouldFailAssignUserSubtypeToOwnerUser() {

        UserCreateRequestDTO userOwnerDTO = UserTestHelper.createValidGenericOwnerRequest();
        UserResponseDTO response = createUser(userOwnerDTO);

        UserSubtypeRequestDTO userSubtypeRequestDTO = new UserSubtypeRequestDTO("USER OWNER");

        String adminToken = authenticateAndGetToken(userOwnerDTO);

        Long createdSubtypeId =
                given()
                        .header("Authorization", "Bearer " + adminToken)
                        .body(userSubtypeRequestDTO)
                        .when()
                        .post("/subtipos")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .jsonPath()
                        .getLong("id");


        AssignUserSubtypeToUserDTO dto = new AssignUserSubtypeToUserDTO(createdSubtypeId);

        given()
                .header("Authorization", "Bearer " + adminToken)
                .body(dto)
                .when()
                .put("/usuarios/{id}/subtipo", response.id())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        CreateUserInput inputOwner = UserPresenter.toInputCreate(dto);
        CreateUserOutput outputOwner = createUserUseCase.execute(inputOwner);
        return UserPresenter.toDto(outputOwner);
    }
}
