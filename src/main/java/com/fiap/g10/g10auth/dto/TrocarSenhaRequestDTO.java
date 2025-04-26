package com.fiap.g10.g10auth.dto;

import jakarta.validation.constraints.NotBlank;

public record TrocarSenhaRequestDTO(
        @NotBlank(message = "Senha atual é obrigatória")
        String senhaAtual,

        @NotBlank(message = "Nova senha é obrigatória")
        String novaSenha
) {}