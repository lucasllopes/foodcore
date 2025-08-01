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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuário", description = "Endpoint para CRUD de usuário")
public interface UserController {

    @Operation(
            description = "Ao passar um id de um usuário como parâmetro, verifica o tipo de usuário de " +
                    "quem está realizando a consulta, se for DONO, " +
                    "ele pode consultar por qualquer usuário, caso contrário, " +
                    "ele poderá apenas pesquisar por ele mesmo.",
            summary = "Retorna o usuário do id específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
                                                    name = "Mensagem de erro para usuário não encontrado",
                                                    summary = "Usuário não encontrado",
                                                    description = "Mensagem de erro para usuário não encontrado",
                                                    value = """
                                                    {
                                                        "mensagem": "Usuário não encontrado"
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
    ResponseEntity<UserResponseDTO> findById(Long id);

    @Operation(
            description = "Ao passar as condições da paginação, verifica o tipo de usuário de quem " +
                    "está fazendo a consulta, se for DONO, " +
                    "será listado os usuários respeitando as condições informadas, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Retorna os usuários de forma paginada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Ok",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
    ResponseEntity<Page<UserResponseDTO>> listPaginatedUsers(@ParameterObject Pageable pageable);

    @Operation(
            description = "Ao passar um json com os dados de um usuário no corpo da requisição, " +
                    "valida os dados fornecidos, caso estejam corretos, retorna os " +
                    "dados do usuário com um id (indicando que ele foi inserido no banco), caso contrário, " +
                    "devolve uma mensagem de erro indicando o porque a inserção não ocorreu.",
            summary = "Cria um usuário com os dados fornecidos",
            requestBody = @RequestBody(
                    description = "Dados do usuário",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserCreateRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados do usuário que será inserido",
                                            summary = "Dados do usuário",
                                            description = "Exemplo de dados do usuário que será inserido",
                                            value = """
                                                    {
                                                         "nome": "USUARIO DE TESTE1 - INSERT 1",
                                                         "email": "usuario1testeinsert1@email.com",
                                                         "login": "teste1insert1",
                                                         "senha": "123456",
                                                         "tipo": "DONO",
                                                         "enderecos": [
                                                             {
                                                                 "logradouro": "Rua 1",
                                                                 "numero": "123",
                                                                 "complemento": "Apto 11",
                                                                 "bairro": "Centro 11",
                                                                 "cep": "01234-000 41",
                                                                 "estado": "SP",
                                                                 "cidade": "São Paulo"
                                                             },
                                                             {
                                                                 "logradouro": "Rua 41-2",
                                                                 "numero": "124",
                                                                 "complemento": "Apto 41-2",
                                                                 "bairro": "Centro 41-2",
                                                                 "cep": "01234-000 41-2",
                                                                 "estado": "SP",
                                                                 "cidade": "São Paulo"
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
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
                                                    name = "Mensagem de erro para indicar que o tipo de usuário " +
                                                            "fornecido não é válido",
                                                    summary = "Tipo de usuário informado não é válido",
                                                    description = "Mensagem de erro para indicar que o tipo de usuário " +
                                                            "fornecido não é válido",
                                                    value = """
                                                    {
                                                        "mensagem": "Valor inválido para tipo de usuário. Valores válidos: [CLIENTE, COLABORADOR, DONO]"
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
                                                            "existe um usuário com esse email",
                                                    summary = "Email já está em uso",
                                                    description = "Mensagem de erro para indicar que já " +
                                                            "existe um usuário com esse email",
                                                    value = """
                                                    {
                                                        "mensagem": "Email já está em uso."
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
                                                            "existe um usuário com esse login",
                                                    summary = "Login já está em uso",
                                                    description = "Mensagem de erro para indicar que já " +
                                                            "existe um usuário com esse login",
                                                    value = """
                                                    {
                                                        "mensagem": "Login já está em uso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<UserResponseDTO> createUser(UserCreateRequestDTO dto);

    @Operation(
            description = "Ao passar um id de um usuário como parâmetro e um json dos dados atualizados no corpo da requisição, " +
                    "verifica se está tentando atualizar o próprio usuário, caso seja, " +
                    "permite a atualização dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Atualiza o usuário do id especificado",
            requestBody = @RequestBody(
                    description = "Dados atualizados do usuário",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserUpdateRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados atualizados do usuário anteriormente inserido",
                                            summary = "Dados atualizados do usuário",
                                            description = "Exemplo de dados atualizados do usuário anteriormente inserido",
                                            value = """
                                                    {
                                                        "nome": "TESTE INSERT 1 MUDOU PARA UPDATE 1",
                                                        "email": "testeinsertmudouaposupdate@email.com",
                                                        "enderecos": [
                                                            {
                                                                "logradouro": "Rua Nova MUDOU",
                                                                "numero": "123 41 MUDOU",
                                                                "complemento": "Apto 41 MUDOU",
                                                                "bairro": "Centro 41 MUDOU",
                                                                "cep": "01234-000 41 MUDOU",
                                                                "estado": "SP",
                                                                "cidade": "São Paulo"
                                                            },
                                                            {
                                                                "logradouro": "Rua Nova 41-2 MUDOU",
                                                                "numero": "123 41-2 MUDOU",
                                                                "complemento": "Apto 41-2 MUDOU",
                                                                "bairro": "Centro 41-2 MUDOU",
                                                                "cep": "01234-000 41-2 MUDOU",
                                                                "estado": "SP",
                                                                "cidade": "São Paulo"
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
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
                                                            "existe um usuário com esse email",
                                                    summary = "Email já está em uso",
                                                    description = "Mensagem de erro para indicar que já " +
                                                            "existe um usuário com esse email",
                                                    value = """
                                                    {
                                                        "mensagem": "Email já está em uso."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<UserResponseDTO> updateUser(Long id, UserUpdateRequestDTO dto);

    @Operation(
            description = "Ao passar um id de um usuário como parâmetro, " +
                    "verifica se está tentando excluir o próprio usuário, caso seja, " +
                    "permite a exclusão dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Exclui o usuário do id especificado"
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
    ResponseEntity<Void> deleteUser(Long id);

    @Operation(
            description = "Ao passar um id de um usuário como parâmetro e um json dos dados para trocar a " +
                    "senha no corpo da requisição, verifica se está tentando alterar o próprio usuário, caso seja, " +
                    "verifica também a senha atual informada é a correta, caso seja, " +
                    "permite a atualização dos dados, caso contrário, " +
                    "retorna uma mensagem de erro dizendo que a senha atual informada está incorreta, " +
                    "e caso quem estiver tentando alterar o usuário, não for o próprio usuário, " +
                    "retorna uma mensagem de erro dizendo que ele não tem permissão.",
            summary = "Troca a senha do usuário do id especificado",
            requestBody = @RequestBody(
                    description = "Dados para troca de senha",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ChangePasswordRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de dados para troca de senha",
                                            summary = "Dados para troca de senha",
                                            description = "Exemplo de dados para troca de senha",
                                            value = """
                                                    {
                                                         "senhaAtual": "123456",
                                                         "novaSenha": "123"
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
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
                    description = "Unauthorized",
                    responseCode = "401",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageErrorDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Mensagem de erro para indicar que a senha " +
                                                            "atual informada não está correta",
                                                    summary = "Usuário não autenticado",
                                                    description = "Mensagem de erro para indicar que a senha " +
                                                            "atual informada não está correta",
                                                    value = """
                                                    {
                                                        "mensagem": "Senha atual incorreta"
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
    ResponseEntity<String> changePassword(Long id, ChangePasswordRequestDTO dto);

    @Operation(
            description = "Ao passar um id de um usuário como parâmetro e um json que contém o id do subtipo de usuário, " +
                    "verifica o tipo de usuário de quem está tentando atualizar, se for DONO, " +
                    "também verifica se o tipo do usuário do id especificado é DONO, caso seja, não permite a " +
                    "atribuição de um subtipo, pois o usuário com tipo DONO, não pode ter um subtipo atribuído, " +
                    "caso o usuário do id especificado não seja do tipo DONO, permite a atribuição do subtipo de usuário " +
                    "pro usuário do id especificado, caso quem esteja tentando atualizar, não seja DONO, retorna uma " +
                    "mensagem de erro dizendo que ele não tem permissão.",
            summary = "Atribui o subtipo de usuário do usuário especificado",
            requestBody = @RequestBody(
                    description = "JSON com o Id do subtipo de usuário",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AssignUserSubtypeToUserDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de json com o id do subtipo de usuário " +
                                                    "que será atribuido ao usuário",
                                            summary = "Id do subtipo de usuário",
                                            description = "Exemplo de json com o id do subtipo de usuário " +
                                                    "que será atribuido ao usuário",
                                            value = """
                                                    {
                                                         "idUserSubtype": 3
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
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
                                                    name = "Mensagem de erro para indicar que usuários com o tipo DONO, " +
                                                            "não podem um subtipo atribuído",
                                                    summary = "Usuários tipo DONO não podem ser subtipos atribuídos",
                                                    description = "Mensagem de erro para indicar que usuários com o tipo DONO, " +
                                                            "não podem um subtipo atribuído",
                                                    value = """
                                                    {
                                                        "mensagem": "Usuários com tipo 'Dono' não podem ter um subtipo atribuído."
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
            )
    })
    ResponseEntity<UserResponseDTO> assignUserSubtype(Long id, AssignUserSubtypeToUserDTO dto);
}
