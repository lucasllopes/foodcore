package com.fiap.foodcore.dto;

public record AddressResponseDTO(String logradouro,
                                 String numero,
                                 String complemento,
                                 String bairro,
                                 String cep,
                                 String estado,
                                 String cidade)
{}
