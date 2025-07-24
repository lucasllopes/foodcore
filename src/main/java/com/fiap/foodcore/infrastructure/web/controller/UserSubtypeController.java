package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Subtipo de Usuário", description = "Endpoint para CRUD de subtipo de usuário")
public interface UserSubtypeController {

    @Operation(
            description = "Ao passar um json com os dados de um subtipo de usuário no corpo da requisição, " +
                    "valida os dados fornecidos, caso estejam corretos, retorna os " +
                    "dados do subtipo de usuário com um id (indicando que ele foi inserido no banco), caso contrário, " +
                    "devolve uma mensagem de erro indicando o porque a inserção não ocorreu.",
            summary = "Cria um subtipo de usuário com os dados fornecidos",
            requestBody = @RequestBody(
                    description = "Dados do subtipo de usuário",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTypeRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados do subtipo de usuário que será inserido",
                                            summary = "Dados do subtipo de usuário",
                                            description = "Exemplo de dados do subtipo de usuário que será inserido",
                                            value = """
                                                    {
                                                          "name":"MECANICO"
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
                                    schema = @Schema(implementation = UserTypeResponseDTO.class)
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
                    description = "Conflict",
                    responseCode = "409",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que já " +
                                                            "existe um subtipo de usuário com esse nome",
                                                    summary = "Nome de subtipo já em uso",
                                                    description = "Mensagem de erro para indicar que já " +
                                                            "existe um subtipo de usuário com esse nome",
                                                    value = """
                                                    {
                                                         "mensagem": "Esse tipo de usuário já está cadastrado"
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<UserTypeResponseDTO> createUserType(UserTypeRequestDTO dto);

    @Operation(
            description = "Ao passar um id de um subtipo de usuário existente e um json com os dados de um subtipo de usuário no corpo da requisição, " +
                    "valida os dados fornecidos, caso estejam corretos, retorna os " +
                    "dados do subtipo de usuário atualizados, caso contrário, " +
                    "devolve uma mensagem de erro indicando o porque a atualização não ocorreu.",
            summary = "Atualiza um subtipo de usuário com os dados fornecidos",
            requestBody = @RequestBody(
                    description = "Dados do subtipo de usuário",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTypeRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados do subtipo de usuário que será inserido",
                                            summary = "Dados do subtipo de usuário",
                                            description = "Exemplo de dados do subtipo de usuário que será inserido",
                                            value = """
                                                    {
                                                          "name":"PILOTO"
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
                                    schema = @Schema(implementation = UpdateUserTypeResponseDTO.class)
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
//                                                            "existe um subtipo de usuário com esse nome",
//                                                    summary = "Nome de subtipo já em uso",
//                                                    description = "Mensagem de erro para indicar que já " +
//                                                            "existe um subtipo de usuário com esse nome",
//                                                    value = """
//                                                    {
//                                                         "mensagem": "Esse tipo de usuário já está cadastrado"
//                                                    }
//                                                    """
//                                            )
//                                    }
//                            )
//                    }
//            )
    })
    ResponseEntity<UpdateUserTypeResponseDTO> updateUser(Long id, UserTypeUpdateRequestDTO dto);

    @Operation(
            description = "Ao passar um id de um subtipo de usuário como parâmetro, " +
                    "verifica se existe um subtipo de usuário com esse id, caso exista, " +
                    "retorna o subtipo, caso contrário, " +
                    "devolve uma mensagem de erro indicando que não existe um subtipo com esse id.",
            summary = "Retorna o subtipo de usuário do id específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserTypeResponseDTO.class)
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
                                                    name = "Mensagem de erro para subtipo de usuário não encontrado",
                                                    summary = "Subtipo de usuário não encontrado",
                                                    description = "Mensagem de erro para subtipo de usuário não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Tipo de usuário não encontrado"
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
    ResponseEntity<UserTypeResponseDTO> findById(Long id);

    @Operation(
            description = "Ao passar um nome de um subtipo de usuário como parâmetro, " +
                    "verifica se existe um subtipo de usuário com esse nome, caso exista, " +
                    "retorna o subtipo, caso contrário, " +
                    "devolve uma mensagem de erro indicando que não existe um subtipo com esse nome.",
            summary = "Retorna o subtipo de usuário do nome específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserTypeResponseDTO.class)
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
                                                    name = "Mensagem de erro para subtipo de usuário não encontrado",
                                                    summary = "Subtipo de usuário não encontrado",
                                                    description = "Mensagem de erro para subtipo de usuário não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Subtipo de usuário não encontrado"
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
    ResponseEntity<List<UserTypeResponseDTO>> findByName(String name);

    @Operation(
            description = "Ao passar um id de um subtipo de usuário como parâmetro, " +
                    "verifica se o subtipo de usuário existe, caso exista, " +
                    "permite a exclusão dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não existe.",
            summary = "Exclui o subtipo de usuário do id especificado"
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
                                                    name = "Mensagem de erro para exclusão não permitida",
                                                    summary = "Erro atualização não permitida",
                                                    description = "Mensagem de erro para exclusão não permitida",
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
    ResponseEntity<Void> deleteUser(Long id);
}
