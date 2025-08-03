package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemUpdateRequestDTO;
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
import org.springframework.security.core.Authentication;

@Tag(name = "Itens do cardápio", description = "Endpoint para CRUD de itens do cardápio")
public interface ItemController {

    @Operation(
            description = "Ao passar as condições da paginação, " +
                    "será listado os itens respeitando as condições informadas",
            summary = "Retorna os itens de forma paginada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ItemCreateResponseDTO.class)
                            )
                    }
            )
    })
    ResponseEntity<Page<ItemCreateResponseDTO>> listItems(@ParameterObject Pageable pageable);

    @Operation(
            description = "Ao passar um id de um item como parâmetro, verifica se o mesmo existe, caso exista, " +
                    "ele retorna os dados do item com aquele id, caso contrário, " +
                    "ele retornará uma mensagem de erro.",
            summary = "Retorna o item do id específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ItemCreateResponseDTO.class)
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
                                                    name = "Mensagem de erro para item não encontrado",
                                                    summary = "Item não encontrado",
                                                    description = "Mensagem de erro para Item não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Item não encontrado."
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
    ResponseEntity<ItemCreateResponseDTO> findById(Long id);

    @Operation(
            description = "Ao passar um json com os dados de um item no corpo da requisição, " +
                    "valida os dados fornecidos, caso estejam corretos, retorna os " +
                    "dados do item com um id (indicando que ele foi inserido no banco), caso contrário, " +
                    "devolve uma mensagem de erro indicando o porque a inserção não ocorreu.",
            summary = "Cria um item com os dados fornecidos",
            requestBody = @RequestBody(
                    description = "Dados do item",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemCreateRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados do item que será inserido",
                                            summary = "Dados do item",
                                            description = "Exemplo de dados do item que será inserido",
                                            value = """
                                                    {
                                                          "name": "Prato Feito - Feijoada",
                                                          "description": "Arroz, feijoada, bife e batata frita",
                                                          "price": 59.90,
                                                          "availability": "DISPONIVEL",
                                                          "photo": "https://exemplo.com/imagem1.jpg"
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
                                    schema = @Schema(implementation = ItemCreateResponseDTO.class)
                            )
                    }
            )
//            ,
//            @ApiResponse(
//                    description = "Conflict",
//                    responseCode = "409",
//                    content = {
//                            @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = MessageErrorDTO.class),
//                                    examples = {
//                                            @ExampleObject(
//                                                    name = "Mensagem de erro para indicar que já " +
//                                                            "existe o tipo de usuário do dono não é DONO",
//                                                    summary = "Tipo de usuário do dono do restaurante não é DONO",
//                                                    description = "Mensagem de erro para indicar que já " +
//                                                            "existe o tipo de usuário do dono não é DONO",
//                                                    value = """
//                                                    {
//                                                        "mensagem": "O usuário informado não é do tipo Dono."
//                                                    }
//                                                    """
//                                            )
//                                    }
//                            )
//                    }
//            )
    })
    ResponseEntity<ItemCreateResponseDTO> createItem(ItemCreateRequestDTO itemDto, Authentication authentication);

    @Operation(
            description = "Ao passar um id de um item como parâmetro e um json dos dados atualizados no corpo da requisição, " +
                    "verifica se está tentando atualizar o próprio item, caso seja, " +
                    "permite a atualização dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Atualiza o item do id especificado",
            requestBody = @RequestBody(
                    description = "Dados atualizados do item",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemUpdateRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados atualizados do item anteriormente inserido",
                                            summary = "Dados atualizados do item",
                                            description = "Exemplo de dados atualizados do item anteriormente inserido",
                                            value = """
                                                    {
                                                        "id": 1,
                                                        "name": "Prato Feito - Feijoada",
                                                        "description": "Arroz, feijoada, bife e batata frita",
                                                        "price": 59.90,
                                                        "availability": "DISPONIVEL",
                                                        "photo": "https://exemplo.com/imagem1.jpg"
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
    ResponseEntity<ItemCreateResponseDTO> updateItem(Long id, ItemUpdateRequestDTO itemDto);

    @Operation(
            description = "Ao passar um id de um item como parâmetro, " +
                    "verifica se está tentando excluir o próprio item, caso seja, " +
                    "permite a exclusão dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Exclui o item do id especificado"
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
    ResponseEntity<Void> deleteItem(Long id);
}
