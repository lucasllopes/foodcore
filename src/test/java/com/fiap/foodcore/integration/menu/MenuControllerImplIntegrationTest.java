package com.fiap.foodcore.integration.menu;


import com.fiap.foodcore.application.usecase.CreateUserUseCase;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.helper.UserTestHelper;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemMenuAssignDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemUpdateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuUpdateRequestDTO;
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
import java.util.List;

import static com.fiap.foodcore.helper.RestaurantTestHelper.createValidRestarantRequest;
import static com.fiap.foodcore.helper.UserTestHelper.authenticateAndGetToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg",
                ownerUser.id()
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
        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg",
                ownerUser.id()

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
    void shouldListMenus() {
        // Cria um menu para garantir que a lista não estará vazia
        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
                1L,
                "Item List",
                "Desc",
                new BigDecimal("15.0"),
                "Disponível",
                "/caminho/foto.jpg",
                ownerUser.id()
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

//    @Test
//    void shouldUpdateMenuSuccessfully(){
//        // Criar um restaurante com nome único
//        String uniqueRestaurantName = "Restaurante Teste " + System.currentTimeMillis();
//        RestaurantCreateRequestDTO restaurantRequest = new RestaurantCreateRequestDTO(
//                uniqueRestaurantName,
//                "Brasileira",
//                new AddressCreateRequestDTO(
//                        "Rua Teste, 123",
//                        "123",
//                        null,
//                        "Centro",
//                        "Cidade Teste",
//                        "00000-000",
//                        "Estado Teste"
//                ),
//                java.time.LocalTime.of(9, 0),
//                java.time.LocalTime.of(18, 0),
//                ownerUser.id() // Usando o ID dinâmico do usuário proprietário
//        );
//
//        // Criar o restaurante
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
//        assertNotNull(restaurantId, "O ID do restaurante não deveria ser nulo");
//
//        // Criar item para o menu
//        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
//                1L,
//                "Item Teste",
//                "Descrição Item",
//                new BigDecimal("10.0"),
//                "Disponível",
//                "/imagens/item-teste.jpg",
//                ownerUser.id() // Usando o ID do usuário proprietário
//        );
//
//        // Criar menu para o restaurante criado
//        MenuCreateRequestDTO createRequest = new MenuCreateRequestDTO(
//                "Menu Teste",
//                "Descrição Teste",
//                restaurantId.longValue(),
//                List.of(item)
//        );
//
//        Integer menuId = given()
//            .header("Authorization", "Bearer " + authToken)
//            .body(createRequest)
//            .when()
//            .post("/cardapios")
//            .then()
//            .statusCode(HttpStatus.CREATED.value())
//            .extract()
//            .path("id");
//
//        assertNotNull(menuId, "O ID do menu não deveria ser nulo");
//
//        // Criar objeto para atualização do menu
//        // Usando o ID do item criado anteriormente
//        var itemUpdateRequestDTO = new ItemUpdateRequestDTO(
//                item.id(), // Usando o ID do item já criado
//                "Item Atualizado",
//                "Nova descrição",
//                new BigDecimal("20.0"),
//                "Disponível",
//                "/imagens/item-atualizado.jpg"
//        );
//
//        // Atualizar o menu
//        MenuUpdateRequestDTO updateRequest = new MenuUpdateRequestDTO(
//                menuId.longValue(),
//                "Menu Atualizado",
//                "Nova descrição",
//                restaurantId.longValue(),
//                List.of(itemUpdateRequestDTO) // Mantendo o mesmo item para simplicidade
//        );
//
//    }

    @Test
    void shouldUpdateMenuSuccessfully() {
         //Criar um usuário com perfil de proprietário
        UserCreateRequestDTO ownerUser = UserTestHelper.createValidOwnerUserToCreateRestaurantRequest();
        UserResponseDTO ownerResponse = createUser(ownerUser);

//        Long ownerId = 2L;
//        String uniqueName = "Item Teste " + System.currentTimeMillis();
//        // Criar mock para User
//        User ownerUser = mock(User.class);
//        when(ownerUser.getId()).thenReturn(ownerId);


        // Obter token de autenticação válido
        String token = UserTestHelper.authenticateAndGetToken(ownerUser);

        // Criar um restaurante real para o teste
        RestaurantCreateRequestDTO restaurantDTO = createValidRestarantRequest(ownerResponse.id());
        Integer restaurantId = given()
                .header("Authorization", "Bearer " + token)
                .body(restaurantDTO)
                .when()
                .post("/restaurantes")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");


        // Usando timestamp para garantir nomes únicos
        String uniqueId = String.valueOf(System.currentTimeMillis());

        ItemCreateRequestDTO itemRequest = new ItemCreateRequestDTO(
                "Item Original " + uniqueId,
                "Desc",
                new BigDecimal("12.0"),
                "Disponível",
                "/foto.jpg",
                ownerResponse.id()// Definindo o ID do dono do item
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

        // Criar item com o dono_id correto (usando o ID do usuário real)
        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
                itemId.longValue(), // Sem ID na criação
                "Item Original " + uniqueId,
                "Desc",
                new BigDecimal("12.0"),
                "Disponível",
                "/foto.jpg",
                ownerResponse.id() // Definindo o ID do dono do item
        );

        // Criar menu com o item
        MenuCreateRequestDTO menuDTO = new MenuCreateRequestDTO(
                "Menu Original",
                "Descrição original",
                restaurantId.longValue(),
                List.of(item)
        );

        // Obter ID do menu criado
        Integer menuId = given()
                .header("Authorization", "Bearer " + token)
                .body(menuDTO)
                .when()
                .post("/cardapios")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
//
//        // Obter o ID do item criado
//        Integer itemId = given()
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .get("/cardapios/{id}", menuId)
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .path("items[0].id");

        // Criar objeto para atualização
        MenuUpdateRequestDTO updateDTO = new MenuUpdateRequestDTO(
                menuId.longValue(),
                "Menu Atualizado",
                "Descrição atualizada",
                restaurantId.longValue(),
                List.of(
                        new ItemUpdateRequestDTO(
                                itemId.longValue(),
                                "Item Atualizado",
                                "Descrição atualizada",
                                new BigDecimal("15.99"),
                                "Disponível",
                                "/imagens/item-atualizado.jpg"
                        )
                )
        );

        // Atualizar o menu - observe o código 200 (não 201)
        given()
                .header("Authorization", "Bearer " + token)
                .body(updateDTO)
                .when()
                .put("/cardapios/{id}", menuId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Menu Atualizado"))
                .body("items[0].name", equalTo("Item Atualizado"));
    }

//    @Test
//    void shouldUpdateMenuSuccessfully() {
//        // Gerar um usuário proprietário específico para este teste
//        UserCreateRequestDTO ownerRequest = UserTestHelper.createValidGenericOwnerRequest();
//        UserResponseDTO owner = createUser(ownerRequest);
//        String testToken = authenticateAndGetToken(ownerRequest);
//
//        // 1. Criar restaurante com nome único
//        String uniqueRestaurantName = "Restaurante Teste " + System.currentTimeMillis();
//        AddressCreateRequestDTO endereco = new AddressCreateRequestDTO(
//                "Rua Teste, 123",
//                "123",
//                null,
//                "Centro",
//                "Cidade Teste",
//                "00000-000",
//                "Estado Teste"
//        );
//
//        RestaurantCreateRequestDTO restaurantRequest = new RestaurantCreateRequestDTO(
//                uniqueRestaurantName,
//                "Brasileira",
//                endereco,
//                java.time.LocalTime.of(9, 0),
//                java.time.LocalTime.of(18, 0),
//                owner.id()
//        );
//
//        // 2. Criar o restaurante usando o token específico
//        Integer restaurantId = given()
//                .header("Authorization", "Bearer " + testToken)
//                .body(restaurantRequest)
//                .when()
//                .post("/restaurantes")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .extract()
//                .path("id");
//
//        assertNotNull(restaurantId, "O ID do restaurante não deveria ser nulo");
//        Long restaurantIdLong = restaurantId.longValue();
//
//        // 3. Criar item para o menu - FORNECENDO UM ID VÁLIDO
//        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
//                1L, // Usar ID válido em vez de null
//                "Item Original",
//                "Descrição original",
//                new BigDecimal("15.99"),
//                "Disponível",
//                "/imagens/item.jpg",
//                owner.id()
//        );
//
//        // 4. Criar menu para o restaurante
//        MenuCreateRequestDTO createRequest = new MenuCreateRequestDTO(
//                "Menu Original",
//                "Descrição original",
//                restaurantIdLong,
//                List.of(item)
//        );
//
//        // 5. Obter ID do menu criado
//        Integer menuId = given()
//                .header("Authorization", "Bearer " + testToken)
//                .body(createRequest)
//                .when()
//                .post("/cardapios")
//                .then()
//                .statusCode(HttpStatus.CREATED.value())
//                .extract()
//                .path("id");
//
//        assertNotNull(menuId, "O ID do menu não deveria ser nulo");
//
//        // 6. Obter o ID do item
//        var menuResponse = given()
//                .header("Authorization", "Bearer " + testToken)
//                .when()
//                .get("/cardapios/{id}", menuId)
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .extract()
//                .response();
//
//        Integer itemId = menuResponse.path("items[0].id");
//        assertNotNull(itemId, "O ID do item não deveria ser nulo");
//
//        // 7. Criar DTO de atualização
//        ItemUpdateRequestDTO updatedItem = new ItemUpdateRequestDTO(
//                itemId.longValue(),
//                "Item Atualizado",
//                "Nova descrição",
//                new BigDecimal("20.0"),
//                "Disponível",
//                "/imagens/item-atualizado.jpg"
//        );
//
//        MenuUpdateRequestDTO updateRequest = new MenuUpdateRequestDTO(
//                menuId.longValue(),
//                "Menu Atualizado",
//                "Nova descrição",
//                restaurantIdLong,
//                List.of(updatedItem)
//        );
//
//        // 8. Atualizar o menu
//        given()
//                .header("Authorization", "Bearer " + testToken)
//                .contentType(ContentType.JSON)
//                .body(updateRequest)
//                .when()
//                .put("/cardapios/{id}", menuId)
//                .then()
//                .log().ifError()
//                .statusCode(HttpStatus.OK.value())
//                .body("name", equalTo("Menu Atualizado"));
//    }

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



        // Criar menu para o restaurante criado
        MenuCreateRequestDTO createRequest = new MenuCreateRequestDTO(
                "Menu Delete",
                "Desc",
                restaurantId.longValue(),
                null
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
        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg",
                ownerUser.id() // Usando o ID do usuário proprietário
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
        ItemMenuAssignDTO item = new ItemMenuAssignDTO(
                1L,
                "Item Teste",
                "Descrição Item",
                new BigDecimal("10.0"),
                "Disponível",
                "/imagens/item-teste.jpg",
                ownerUser.id()
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
