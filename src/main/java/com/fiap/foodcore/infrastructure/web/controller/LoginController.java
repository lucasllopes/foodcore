package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.infrastructure.web.controller.dto.LoginRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.MessageErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Endpoint para autenticação do usuário")
public interface LoginController {

    @Operation(
            description = "Ao passar um login e uma senha, verifica se as " +
                            "credenciais do usuário estão corretas, e devolve um token de acesso.",
            summary = "Autenticar o usuário",
            requestBody = @RequestBody(
                    description = "Credenciais do usuário",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de Login",
                                            summary = "Credenciais válidas",
                                            description = "Login e senha de um usuário válido",
                                            value = """
                                                    {
                                                        "login": "teste1insert1",
                                                        "senha": "123456"
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
                                    schema = @Schema(implementation = String.class)
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
                                                    name = "Mensagem de erro para credenciais inválidas",
                                                    summary = "Erro credenciais inválidas",
                                                    description = "Mensagem de erro para credenciais inválidas",
                                                    value = """
                                                    {
                                                        "mensagem": "Usuário inexistente ou senha inválida"
                                                    }
                                                    """
                                            )
                                    }
                            )
                    }
            )
    })
    ResponseEntity<String> login(LoginRequestDTO request);
}
