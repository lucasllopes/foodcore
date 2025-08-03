package com.fiap.foodcore.integration;


import com.fiap.foodcore.application.usecase.CreateUserUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemUpdateRequestDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ItemControllerImplIntegrationTest {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @LocalServerPort
    private int port;

    private String authToken;
    private UserResponseDTO ownerUser;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        ownerUser = createUser(UserPresenter.toInputCreate(owner));
        authToken = authenticateAndGetToken(owner);
    }

    public UserResponseDTO createUser(CreateUserInput input) {
        return UserPresenter.toDto(createUserUseCase.execute(input));
    }

    @Test
    void shouldCreateItemSuccessfully() {
        String uniqueName = "Item Teste " + System.currentTimeMillis();

        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                null,
                uniqueName,
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg"
        );

        given()
                .header("Authorization", "Bearer " + authToken)
                .body(itemRequest)
                .when()
                .post("/cardapios/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(uniqueName))
                .body("description", equalTo("Descrição Item"));
    }

    @Test
    void shouldCreateItemSuccessfullyShowId() {
        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                null,
                "Item Teste Show Id",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg"
        );

        Integer id = given()
                .header("Authorization", "Bearer " + authToken)
                .body(itemRequest)
                .when()
                .post("/cardapios/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("id");

        assertNotNull(id, "O ID do item não deveria ser nulo");
    }

    @Test
    void shouldListItems() {
        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                null,
                "Item List",
                "Desc",
                new BigDecimal("15.0"),
                "Disponível",
                "/caminho/foto.jpg"
        );

        given()
                .header("Authorization", "Bearer " + authToken)
                .body(itemRequest)
                .post("/cardapios/items");

        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", not(empty()));
    }


    @Test
    void shouldFindItemById() {
        Long requestId = 1L;
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                null,
                "Item List",
                "Desc",
                new BigDecimal("15.0"),
                "Disponível",
                "/caminho/foto.jpg"
        );

        var response = given()
                .header("Authorization", "Bearer " + authToken)
                .body(item)
                .post("/cardapios/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        System.out.println("Resposta do POST: " + response.asString());

        // Ajuste o nome do campo conforme o JSON retornado
        Integer id = response.path("id");

        assertNotNull(id, "O ID do item não deveria ser nulo");

        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios/items/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id));
    }

    @Test
    void shouldUpdateItemSuccessfully() {
        // Usando timestamp para garantir nomes únicos
        String uniqueId = String.valueOf(System.currentTimeMillis());

        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                null,
                "Item Original " + uniqueId,
                "Desc",
                new BigDecimal("12.0"),
                "Disponível",
                "/foto.jpg"
        );

        Integer itemId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(itemRequest)
                .post("/cardapios/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("id");

        assertNotNull(itemId);

        // Pequena pausa para garantir timestamps diferentes
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ItemUpdateRequestDTO updateRequest = new ItemUpdateRequestDTO(
                itemId.longValue(),
                "Item Atualizado " + uniqueId,
                "Nova descrição",
                new BigDecimal("18.0"),
                "Indisponível",
                "/foto-nova.jpg"
        );

        given()
                .header("Authorization", "Bearer " + authToken)
                .body(updateRequest)
                .when()
                .put("/cardapios/items/{id}", itemId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Item Atualizado " + uniqueId))
                .body("availability", equalTo("Indisponível"));
    }

    @Test
    void shouldDeleteItemSuccessfully() {
        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                null,
                "Item Delete",
                "Desc",
                new BigDecimal("22.0"),
                "Disponível",
                "/foto.jpg"
        );

        Integer itemId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(itemRequest)
                .post("/cardapios/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("id");

        assertNotNull(itemId);

        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .delete("/cardapios/items/{id}", itemId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios/items/{id}", itemId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldReturnForbiddenWhenUserIsNotOwner() {
        // Criando um usuário cliente sem permissão ROLE_DONO
        UserCreateRequestDTO client = UserTestHelper.createValidGenericClientRequest();
        createUser(UserPresenter.toInputCreate(client));
        String clientToken = authenticateAndGetToken(client);

        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                null,
                "Item Cliente " + System.currentTimeMillis(),
                "Desc",
                new BigDecimal("15.0"),
                "Disponível",
                "/foto.jpg"
        );

        given()
                .header("Authorization", "Bearer " + clientToken)
                .body(itemRequest)
                .when()
                .post("/cardapios/items")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void shouldReturnUnauthorizedWhenCreateItemWithoutAuthentication() {
        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                null,
                "Item Test Unauthorized",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg"
        );

        given()
                .body(itemRequest)
                .when()
                .post("/cardapios/items")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void shouldReturnNotFoundWhenItemDoesNotExist() {
        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios/items/9999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldListItemsWithPaginationAndSorting() {
        // Criar múltiplos itens
        for (int i = 1; i <= 5; i++) {
            ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                    null,
                    "Item Paginado " + i,
                    "Desc " + i,
                    new BigDecimal("10.0").add(new BigDecimal(i)),
                    "Disponível",
                    "/foto" + i + ".jpg"
            );

            given()
                    .header("Authorization", "Bearer " + authToken)
                    .body(item)
                    .post("/cardapios/items");
        }

        // Testar paginação (página 0, tamanho 2, ordenado por nome)
        given()
                .header("Authorization", "Bearer " + authToken)
                .param("page", 0)
                .param("size", 2)
                .param("sort", "name,asc")
                .when()
                .get("/cardapios/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content.size()", equalTo(2))
                .body("totalPages", greaterThanOrEqualTo(3))
                .body("totalElements", greaterThanOrEqualTo(5));
    }

}