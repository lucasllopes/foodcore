package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.infrastructure.web.controller.dto.MessageErrorDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

@Tag(name = "Restaurante", description = "Endpoint para CRUD de restaurante")
public interface RestaurantController {

    @Operation(
            description = "Ao passar um id de um restaurante como parâmetro, verifica se o mesmo existe, caso exista, " +
                    "ele retorna os dados do restaurante com aquele id, caso contrário, " +
                    "ele retornará uma mensagem de erro.",
            summary = "Retorna o restaurante do id específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestaurantResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    description = "Not Found",
                    responseCode = "404",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para restaurante não encontrado",
                                                    summary = "Restaurante não encontrado",
                                                    description = "Mensagem de erro para restaurante não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Restaurante não encontrado."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<RestaurantResponseDTO> findRestaurantById(Long id);

    @Operation(
            description = "Ao passar as condições da paginação, " +
                    "será listado os restaurantes respeitando as condições informadas. " +
                    "Também existe a possibilidade de passar um nome de restaurante como filtro.",
            summary = "Retorna os restaurantes de forma paginada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestaurantResponseDTO.class)
                            )
                    }
            )
    })
    ResponseEntity<Page<RestaurantResponseDTO>> listPaginatedRestaurants(String name, @ParameterObject Pageable pageable);

    @Operation(
            description = "Ao passar um json com os dados de um restaurante no corpo da requisição, " +
                    "verifica se o tipo do usuário que está criando um restaurante, é do tipo DONO, caso não seja, " +
                    "devolve uma mensagem de erro dizendo que o mesmo não tem permissão para isso, mas caso seja, " +
                    "valida os dados fornecidos, caso estejam corretos, retorna os " +
                    "dados do restaurante com um id (indicando que ele foi inserido no banco), caso contrário, " +
                    "devolve uma mensagem de erro indicando o porque a inserção não ocorreu.",
            summary = "Cria um restaurante com os dados fornecidos",
            requestBody = @RequestBody(
                    description = "Dados do restaurante",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantCreateRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados do restaurante que será inserido",
                                            summary = "Dados do restaurante",
                                            description = "Exemplo de dados do restaurante que será inserido",
                                            value = """
                                                    {
                                                          "nome": "Paradise",
                                                          "cuisineType": "FAST FOOD",
                                                          "endereco": {
                                                              "logradouro": "Rua Dos Lanches 3",
                                                              "numero": "12352",
                                                              "complemento": "Apto 1212",
                                                              "bairro": "Centro 1211",
                                                              "cep": "01234-000 241",
                                                              "estado": "GO",
                                                              "cidade": "Itumbiara"
                                                          },
                                                          "openingHours": "19:00",
                                                          "closingHours": "23:59",
                                                          "ownerId": 6
                                                     }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestaurantResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    description = "Bad Request",
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que " +
                                                            "o tipo de usuário informado no ownerId não é DONO",
                                                    summary = "Tipo de usuário do dono do restaurante não é DONO",
                                                    description = "Mensagem de erro para indicar que " +
                                                            "o tipo de usuário informado no ownerId não é DONO",
                                                    value = """
                                                    {
                                                        "mensagem": "O código de usuário informado não é do tipo DONO."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Unauthorized",
                    responseCode = "401",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que não está autenticado",
                                                    summary = "Usuário não autenticado",
                                                    description = "Mensagem de erro para indicar que não está autenticado",
                                                    value = """
                                                    {
                                                        "status": 401,
                                                        "error": "Unauthorized",
                                                        "message": "Você precisa estar autenticado para acessar este recurso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Forbidden",
                    responseCode = "403",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para consulta não permitida",
                                                    summary = "Erro consulta não permitida",
                                                    description = "Mensagem de erro para consulta não permitida",
                                                    value = """
                                                    {
                                                        "status": 403,
                                                        "error": "Forbidden",
                                                        "message": "Você não tem permissão para acessar este recurso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<RestaurantResponseDTO> createRestaurant(RestaurantCreateRequestDTO dto);

    @Operation(
            description = "Ao passar um id de um restaurante como parâmetro e um json dos dados atualizados no corpo da requisição, " +
                    "verifica se quem está tentando atualizar o restaurante, é o próprio dono do restaurante, caso seja, " +
                    "permite a atualização dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que apenas o dono do restaurante pode atualizar.",
            summary = "Atualiza o restaurante do id especificado",
            requestBody = @RequestBody(
                    description = "Dados atualizados do restaurante",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantUpdateRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados atualizados do restaurante anteriormente inserido",
                                            summary = "Dados atualizados do restaurante",
                                            description = "Exemplo de dados atualizados do restaurante anteriormente inserido",
                                            value = """
                                                    {
                                                        "name": "MacBurguer",
                                                        "cuisineType": "FAST FOOD",
                                                        "endereco": {
                                                            "logradouro": "Rua Dos Lanches",
                                                            "numero": "1235",
                                                            "complemento": "Apto 121",
                                                            "bairro": "Centro 121",
                                                            "cep": "01234-000 21",
                                                            "estado": "GO",
                                                            "cidade": "Itumbiara"
                                                        },
                                                        "openingHours": "19:00",
                                                        "closingHours": "00:30"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestaurantResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    description = "Bad Request",
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que apenas " +
                                                            "o dono do restaurante tem permissão " +
                                                            "para realizar a atualização",
                                                    summary = "Usuário atual não é o proprietário do restaurante",
                                                    description = "Mensagem de erro para indicar que apenas " +
                                                            "o dono do restaurante tem permissão " +
                                                            "para realizar a atualização",
                                                    value = """
                                                    {
                                                        "mensagem": "Apenas o proprietário do restaurante tem permissão para realizar essa atualização."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Unauthorized",
                    responseCode = "401",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que não está autenticado",
                                                    summary = "Usuário não autenticado",
                                                    description = "Mensagem de erro para indicar que não está autenticado",
                                                    value = """
                                                    {
                                                        "status": 401,
                                                        "error": "Unauthorized",
                                                        "message": "Você precisa estar autenticado para acessar este recurso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Forbidden",
                    responseCode = "403",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para atualização não permitida",
                                                    summary = "Erro atualização não permitida",
                                                    description = "Mensagem de erro para atualização não permitida",
                                                    value = """
                                                    {
                                                        "status": 403,
                                                        "error": "Forbidden",
                                                        "message": "Você não tem permissão para acessar este recurso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Not Found",
                    responseCode = "404",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para restaurante não encontrado",
                                                    summary = "Restaurante não encontrado",
                                                    description = "Mensagem de erro para restaurante não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Restaurante não encontrado."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Conflict",
                    responseCode = "409",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que já " +
                                                            "existe um restaurante com esse nome",
                                                    summary = "Já existe um restaurante cadastrado com este nome.",
                                                    description = "Mensagem de erro para indicar que já " +
                                                            "existe um restaurante com esse nome",
                                                    value = """
                                                    {
                                                        "mensagem": "Já existe um restaurante cadastrado com este nome."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<RestaurantResponseDTO> updateRestaurant(Long id, RestaurantUpdateRequestDTO dto, Principal principal);

    @Operation(
            description = "Ao passar um id de um restaurante como parâmetro, " +
                    "verifica se quem está tentando excluir o restaurante, é o próprio dono do restaurante, caso seja, " +
                    "permite a exclusão dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que apenas o dono do restaurante pode excluir.",
            summary = "Exclui o restaurante do id especificado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "No Content",
                    responseCode = "204",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    description = "Bad Request",
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que apenas " +
                                                            "o dono do restaurante tem permissão " +
                                                            "para realizar a exclusão",
                                                    summary = "Usuário atual não é o proprietário do restaurante",
                                                    description = "Mensagem de erro para indicar que apenas " +
                                                            "o dono do restaurante tem permissão " +
                                                            "para realizar a exclusão",
                                                    value = """
                                                    {
                                                        "mensagem": "Apenas o proprietário do restaurante tem permissão para realizar essa exclusão."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Unauthorized",
                    responseCode = "401",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que não está autenticado",
                                                    summary = "Usuário não autenticado",
                                                    description = "Mensagem de erro para indicar que não está autenticado",
                                                    value = """
                                                    {
                                                        "status": 401,
                                                        "error": "Unauthorized",
                                                        "message": "Você precisa estar autenticado para acessar este recurso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Forbidden",
                    responseCode = "403",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para atualização não permitida",
                                                    summary = "Erro atualização não permitida",
                                                    description = "Mensagem de erro para atualização não permitida",
                                                    value = """
                                                    {
                                                        "status": 403,
                                                        "error": "Forbidden",
                                                        "message": "Você não tem permissão para acessar este recurso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    description = "Not Found",
                    responseCode = "404",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para restaurante não encontrado",
                                                    summary = "Restaurante não encontrado",
                                                    description = "Mensagem de erro para restaurante não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Restaurante não encontrado."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<Void> deleteRestaurant(Long id, Principal principal);
}
