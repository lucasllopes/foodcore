package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.menu.MenuUpdateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.MessageErrorDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantResponseDTO;
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

@Tag(name = "Cardápio", description = "Endpoint para CRUD de Cardápio")
public interface MenuController {

    @Operation(
            description = "Ao passar as condições da paginação, " +
                    "será listado os cardápios respeitando as condições informadas",
            summary = "Retorna os cardápios de forma paginada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MenuResponseDTO.class)
                            )
                    }
            )
    })
    ResponseEntity<Page<MenuResponseDTO>> listMenus(@ParameterObject Pageable pageable);

    @Operation(
            description = "Ao passar um id de um cardápio como parâmetro, verifica se o mesmo existe, caso exista, " +
                    "ele retorna os dados do cardápio com aquele id, caso contrário, " +
                    "ele retornará uma mensagem de erro.",
            summary = "Retorna o cardápio do id específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MenuResponseDTO.class)
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
                                                    name = "Mensagem de erro para cardápio não encontrado",
                                                    summary = "Cardápio não encontrado",
                                                    description = "Mensagem de erro para cardápio não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Menu não encontrado."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<MenuResponseDTO> findMenuById(Long id);

    @Operation(
            description = "Ao passar um json com os dados de um cardápio no corpo da requisição, " +
                    "valida os dados fornecidos, caso estejam corretos, retorna os " +
                    "dados do cardápio com um id (indicando que ele foi inserido no banco), caso contrário, " +
                    "devolve uma mensagem de erro indicando o porque a inserção não ocorreu.",
            summary = "Cria um cardápio com os dados fornecidos",
            requestBody = @RequestBody(
                    description = "Dados do cardápio",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados do cardápio que será inserido",
                                            summary = "Dados do cardápio",
                                            description = "Exemplo de dados do cardápio que será inserido",
                                            value = """
                                                    {
                                                        "name": "Cardápio Executivo 21072025",
                                                        "description": "Opções para o almoço",
                                                        "restaurantId": 1,
                                                        "items": [
                                                          {
                                                            "name": "Prato Feito",
                                                            "description": "Arroz, feijão, bife e batata frita",
                                                            "price": 29.90,
                                                            "availability": "DISPONIVEL",
                                                            "photo": "https://exemplo.com/imagem1.jpg"
                                                          },
                                                          {
                                                            "name": "Salada Caesar",
                                                            "description": "Alface, frango grelhado, parmesão e croutons",
                                                            "price": 22.50,
                                                            "availability": "INDISPONIVEL",
                                                            "photo": "https://exemplo.com/imagem2.jpg"
                                                          }
                                                        ]
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
                                    schema = @Schema(implementation = MenuResponseDTO.class)
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
                                                    name = "Mensagem de erro para indicar que  " +
                                                            "o restaurante informado não foi encontrado",
                                                    summary = "Restaurante informado não encontrado",
                                                    description = "Mensagem de erro para indicar que  " +
                                                            "o restaurante informado não foi encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Restaurant not found with ID: 55."
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
                                                            "existe um cardápio com o mesmo nome " +
                                                            "para o restaurante informado",
                                                    summary = "Nome de cardápio já existente para restaurante informado",
                                                    description = "Mensagem de erro para indicar que já " +
                                                            "existe um cardápio com o mesmo nome " +
                                                            "para o restaurante informado",
                                                    value = """
                                                    {
                                                        "mensagem": "Menu with name 'Cardápio Executivo 21072025' already exists for restaurant ID: 1"
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<MenuResponseDTO> createMenu(MenuCreateRequestDTO menuDto);

    @Operation(
            description = "Ao passar um id de um cardápio como parâmetro e um json dos dados atualizados no corpo da requisição, " +
                    "verifica se está tentando atualizar o próprio cardápio, caso seja, " +
                    "permite a atualização dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Atualiza o cardápio do id especificado",
            requestBody = @RequestBody(
                    description = "Dados atualizados do cardápio",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MenuUpdateRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados atualizados do cardápio anteriormente inserido",
                                            summary = "Dados atualizados do cardápio",
                                            description = "Exemplo de dados atualizados do cardápio anteriormente inserido",
                                            value = """
                                                    {
                                                        "id": 1,
                                                        "name": "Cardápio Executivo 21072025 MUDOU",
                                                        "description": "Opções para o JANTAR",
                                                        "restaurantId": 1,
                                                        "items": [
                                                            {
                                                                "id": 1,
                                                                "name": "Prato Feito",
                                                                "description": "Arroz, feijão, bife e batata frita",
                                                                "price": 29.90,
                                                                "availability": "DISPONIVEL",
                                                                "photo": "https://exemplo.com/imagem1.jpg"
                                                            }
                                                        ]
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
            )
    })
    ResponseEntity<MenuResponseDTO> updateMenu(Long id, MenuUpdateRequestDTO menuDto);

    @Operation(
            description = "Ao passar um id de um cardápio como parâmetro, " +
                    "verifica se está tentando excluir o próprio cardápio, caso seja, " +
                    "permite a exclusão dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Exclui o cardápio do id especificado"
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
            )
    })
    ResponseEntity<Void> deleteMenu(Long id);
}
