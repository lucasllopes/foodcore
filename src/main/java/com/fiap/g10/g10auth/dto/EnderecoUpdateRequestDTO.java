package com.fiap.g10.g10auth.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoUpdateRequestDTO(@NotBlank(message = "Logradouro é obrigatório")
                                       String logradouro,

                                       @NotBlank(message = "Número é obrigatório")
                                       String numero,

                                       String complemento,

                                       @NotBlank(message = "Bairro é obrigatório")
                                       String bairro,

                                       @NotBlank(message = "Cidade é obrigatória")
                                       String cidade,

                                       @NotBlank(message = "Estado é obrigatório")
                                       String estado,

                                       @NotBlank(message = "CEP é obrigatório")
                                       String cep) {
}
