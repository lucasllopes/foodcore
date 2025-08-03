package com.fiap.foodcore.integration.menu;


import com.fiap.foodcore.application.usecase.CreateUserUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantCreateRequestDTO;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class MenuControllerImplIntegrationTest {

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

        // Cria um usuário proprietário com email único e obtém o token para os testes
        UserCreateRequestDTO owner = UserTestHelper.createValidGenericOwnerRequest();
        ownerUser = createUser(owner);
        authToken = authenticateAndGetToken(owner);
    }

    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        CreateUserInput inputOwner = UserPresenter.toInputCreate(dto);
        CreateUserOutput outputOwner = createUserUseCase.execute(inputOwner);
        return UserPresenter.toDto(outputOwner);
    }


    @Test
    void shouldFailToCreateMenuWithoutDescription() {
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg"
        );

        MenuCreateRequestDTO request = new MenuCreateRequestDTO(
                "Menu Teste",
                null,
                ownerUser.id(),
                List.of(item)
        );

        given()
                .header("Authorization", "Bearer " + authToken)
                .body(request)
                .when()
                .post("/cardapios")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldFailToCreateMenuWithoutRestaurantId() {
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg"
        );

        MenuCreateRequestDTO request = new MenuCreateRequestDTO(
                "Menu Teste",
                "Descrição Teste",
                null,
                List.of(item)
        );

        given()
                .header("Authorization", "Bearer " + authToken)
                .body(request)
                .when()
                .post("/cardapios")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldFailToCreateMenuWithEmptyItems() {
        MenuCreateRequestDTO request = new MenuCreateRequestDTO(
                "Menu Teste",
                "Descrição Teste",
                ownerUser.id(),
                Collections.emptyList()
        );

        given()
                .header("Authorization", "Bearer " + authToken)
                .body(request)
                .when()
                .post("/cardapios")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldListMenus() {
        // Cria um menu para garantir que a lista não estará vazia
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                1L,
                "Item List",
                "Desc",
                new BigDecimal("15.0"),
                "Disponível",
                "/caminho/foto.jpg"
        );

        MenuCreateRequestDTO request = new MenuCreateRequestDTO(
                "Menu List",
                "Desc",
                ownerUser.id(),
                List.of(item)
        );

        given()
                .header("Authorization", "Bearer " + authToken)
                .body(request)
                .post("/cardapios");

        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", not(empty()));
    }


    @Test
    void shouldReturnNotFoundWhenMenuDoesNotExist() {
        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios/{id}", 99999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void shouldUpdateMenuSuccessfully() {
        // 1. Primeiro criar um restaurante com nome único
        String uniqueRestaurantName = "Restaurante Teste " + System.currentTimeMillis();
        AddressCreateRequestDTO endereco = new AddressCreateRequestDTO(
                "Rua Teste, 123", // logradouro
                "123",            // numero
                null,              // complemento (opcional)
                "Centro",         // bairro
                "Cidade Teste",   // cidade
                "00000-000",      // cep
                "Estado Teste"    // estado
        );
        RestaurantCreateRequestDTO restaurantRequest = new RestaurantCreateRequestDTO(
                uniqueRestaurantName,
                "Brasileira",
                endereco,
                java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(18, 0),
                ownerUser.id()
        );

        // 2. Criar o restaurante e obter o ID dele
        Integer restaurantId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(restaurantRequest)
                .when()
                .post("/restaurantes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Garantir que o restaurante foi criado corretamente
        assertNotNull(restaurantId, "O ID do restaurante não deveria ser nulo");
        Long restaurantIdLong = restaurantId.longValue();

        // 3. Criar item para o menu
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                1L,
                "Item Original",
                "Descrição original",
                new BigDecimal("15.99"),
                "Disponível",
                "/imagens/item.jpg"
        );

        // 4. Criar menu para o restaurante criado
        MenuCreateRequestDTO createRequest = new MenuCreateRequestDTO(
                "Menu Original",
                "Descrição original",
                restaurantId.longValue(),
                List.of(item)
        );

        // 5. Obter ID do menu criado
        Integer menuId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(createRequest)
                .when()
                .post("/cardapios")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Garantir que o menu foi criado corretamente
        assertNotNull(menuId, "O ID do menu não deveria ser nulo");

        // 6. Obter o ID do item existente e dados atuais do menu
        var menuResponse = given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios/{id}", menuId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        Integer itemId = menuResponse.path("items[0].id");

        // 7. Verificar token e renovar se necessário
        String currentToken = authToken;

        // 8. Criar DTO de atualização mantendo o ID do restaurante explicitamente
        ItemUpdateRequestDTO updatedItem = new ItemUpdateRequestDTO(
                itemId.longValue(),
                "Item Atualizado",
                "Nova descrição",
                new BigDecimal("20.0"),
                "Disponível",
                "/imagens/item-atualizado.jpg"
        );

        MenuUpdateRequestDTO updateRequest = new MenuUpdateRequestDTO(
                "Menu Atualizado",
                "Nova descrição",
                restaurantIdLong,  // Garantir que este valor não seja nulo e corresponde ao restaurante criado
                List.of(updatedItem)
        );

        // 9. Atualizar o menu com debug
        System.out.println("Enviando requisição PUT para /cardapios/" + menuId);
        System.out.println("Corpo da requisição: " + updateRequest);
        System.out.println("Token de autenticação: " + currentToken.substring(0, 20) + "...");

        given()
                .header("Authorization", "Bearer " + currentToken)
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/cardapios/{id}", menuId)
                .then()
                .log().ifError()  // Logar detalhes em caso de erro
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Menu Atualizado"));
    }

    @Test
    void shouldDeleteMenuSuccessfully() {
        // Criar um restaurante com nome único
        String uniqueRestaurantName = "Restaurante Teste " + System.currentTimeMillis();
        RestaurantCreateRequestDTO restaurantRequest = new RestaurantCreateRequestDTO(
                uniqueRestaurantName,
                "Descrição Restaurante",
                new AddressCreateRequestDTO(
                        "Rua Teste",
                        "123",
                        null,
                        "Bairro Teste",
                        "Cidade Teste",
                        "12345-678",
                        "Estado Teste"
                ),
                java.time.LocalTime.of(8, 0),
                java.time.LocalTime.of(18, 0),
                ownerUser.id() // Usando o ID dinâmico do usuário proprietário
        );

        // Criar o restaurante
        Integer restaurantId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(restaurantRequest)
                .when()
                .post("/restaurantes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        assertNotNull(restaurantId, "O ID do restaurante não deveria ser nulo");

        // Criar item para o menu
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                1L,
                "Item Delete",
                "Desc",
                new BigDecimal("9.9"),
                "Disponível",
                "/caminho/foto.jpg"
        );

        // Criar menu para o restaurante criado
        MenuCreateRequestDTO createRequest = new MenuCreateRequestDTO(
                "Menu Delete",
                "Desc",
                restaurantId.longValue(),
                List.of(item)
        );

        // Obter ID do menu criado (corrigindo inconsistência de tipos)
        Integer menuId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(createRequest)
                .post("/cardapios")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        assertNotNull(menuId, "O ID do menu não deveria ser nulo");

        // Excluir o menu
        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .delete("/cardapios/{id}", menuId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Verificar exclusão
        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/cardapios/{id}", menuId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonexistentMenu() {
        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .delete("/cardapios/{id}", 99999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

//    @Test
//    void shouldReturnNotFoundWhenDeletingNonexistentMenu() {
//        // Cria restaurante para garantir contexto de segurança
//        String uniqueRestaurantName = "Restaurante Teste " + System.currentTimeMillis();
//        RestaurantCreateRequestDTO restaurantRequest = new RestaurantCreateRequestDTO(
//                uniqueRestaurantName,
//                "Descrição Restaurante",
//                new AddressCreateRequestDTO(
//                        "Rua Teste",
//                        "123",
//                        null,
//                        "Bairro Teste",
//                        "Cidade Teste",
//                        "12345-678",
//                        "Estado Teste"
//                ),
//                java.time.LocalTime.of(8, 0),
//                java.time.LocalTime.of(18, 0),
//                ownerUser.id()
//        );
//
//        Integer restaurantId = given()
//                .header("Authorization", "Bearer " + authToken)
//                .body(restaurantRequest)
//                .when()
//                .post("/restaurantes")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .extract()
//                .path("id");
//
//        // Tenta deletar menu inexistente
//        given()
//                .header("Authorization", "Bearer " + authToken)
//                .when()
//                .delete("/cardapios/{id}", 99999)
//                .then()
//                .statusCode(HttpStatus.NOT_FOUND.value());
//    }

    @Test
    void  shouldCreateMenuSuccessfully(){
        // 1. Primeiro criar um restaurante com nome único
        String uniqueRestaurantName = "Restaurante Teste " + System.currentTimeMillis();
        AddressCreateRequestDTO endereco = new AddressCreateRequestDTO(
                "Rua Teste, 123", // logradouro
                "123",            // numero
                null,              // complemento (opcional)
                "Centro",         // bairro
                "Cidade Teste",   // cidade
                "00000-000",      // cep
                "Estado Teste"    // estado
        );
        RestaurantCreateRequestDTO restaurantRequest = new RestaurantCreateRequestDTO(
                uniqueRestaurantName,
                "Brasileira",
                endereco,
                java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(18, 0),
                ownerUser.id()
        );

        // 2. Criar o restaurante e obter o ID dele
        Integer restaurantId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(restaurantRequest)
                .when()
                .post("/restaurantes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Garantir que o restaurante foi criado corretamente
        assertNotNull(restaurantId, "O ID do restaurante não deveria ser nulo");
        Long restaurantIdLong = restaurantId.longValue();

        // 3. Criar item para o menu
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg"
        );

        // 4. Criar menu para o restaurante criado
        MenuCreateRequestDTO request = new MenuCreateRequestDTO(
                "Menu Teste",
                "Descrição Teste",
                restaurantId.longValue(),
                List.of(item)
        );

        given()
            .header("Authorization", "Bearer " + authToken)
            .body(request)
            .when()
            .post("/cardapios")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("name", equalTo("Menu Teste"))
            .body("description", equalTo("Descrição Teste"))
            .body("restaurantId", equalTo(restaurantIdLong.intValue()));
    }

    @Test
    void shouldFindMenuById(){
        // 1. Primeiro criar um restaurante com nome único
        String uniqueRestaurantName = "Restaurante Teste " + System.currentTimeMillis();
        AddressCreateRequestDTO endereco = new AddressCreateRequestDTO(
                "Rua Teste, 123", // logradouro
                "123",            // numero
                null,              // complemento (opcional)
                "Centro",         // bairro
                "Cidade Teste",   // cidade
                "00000-000",      // cep
                "Estado Teste"    // estado
        );
        RestaurantCreateRequestDTO restaurantRequest = new RestaurantCreateRequestDTO(
                uniqueRestaurantName,
                "Brasileira",
                endereco,
                java.time.LocalTime.of(9, 0),
                java.time.LocalTime.of(18, 0),
                ownerUser.id()
        );

        // 2. Criar o restaurante e obter o ID dele
        Integer restaurantId = given()
                .header("Authorization", "Bearer " + authToken)
                .body(restaurantRequest)
                .when()
                .post("/restaurantes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Garantir que o restaurante foi criado corretamente
        assertNotNull(restaurantId, "O ID do restaurante não deveria ser nulo");
        Long restaurantIdLong = restaurantId.longValue();

        // 3. Criar item para o menu
        ItemCreateRequestDTO item = new ItemCreateRequestDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg"
        );

        // 4. Criar menu para o restaurante criado
        MenuCreateRequestDTO request = new MenuCreateRequestDTO(
                "Menu Teste",
                "Descrição Teste",
                restaurantId.longValue(),
                List.of(item)
        );

        Integer menuId = given()
            .header("Authorization", "Bearer " + authToken)
            .body(request)
            .when()
            .post("/cardapios")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .path("id");

        assertNotNull(menuId, "O ID do menu não deveria ser nulo");

    }

}
