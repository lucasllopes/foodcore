package com.fiap.foodcore.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressCreateRequestDTO(

    @NotBlank(message = "Logradouro é obrigatório")
    String logradouro,

    @NotBlank(message = "Número é obrigatório")
    String numero,

    String complemento,

    @NotBlank(message = "Bairro é obrigatório")
    String bairro,

    @NotBlank(message = "Cidade é obrigatória")
    String cidade,

    @NotBlank(message = "CEP é obrigatório")
    String cep,

    @NotBlank(message = "Estado é obrigatório")
    String estado
){}

