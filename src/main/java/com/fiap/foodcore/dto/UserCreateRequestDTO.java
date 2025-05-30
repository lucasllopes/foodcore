package com.fiap.foodcore.dto;

import com.fiap.foodcore.persistence.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserCreateRequestDTO(
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
        String tipo,

        @NotNull(message = "Necessário informar um endereço.")
        List<AddressCreateRequestDTO> enderecos

) {}