package com.fiap.g10.g10auth.dto;

import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioCreateRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Login é obrigatório")
        String login,

        @NotBlank(message = "Senha é obrigatória")
        String senha,

        @NotNull(message = "Tipo de usuário é obrigatório")
        TipoUsuario tipo,

        @NotNull(message = "Necessário informar um endereço.")
        List<EnderecoCreateRequestDTO> enderecos

) {}