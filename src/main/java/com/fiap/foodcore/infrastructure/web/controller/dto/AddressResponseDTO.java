package com.fiap.foodcore.infrastructure.web.controller.dto;

public record AddressResponseDTO(String logradouro,
                                 String numero,
                                 String complemento,
                                 String bairro,
                                 String cep,
                                 String estado,
                                 String cidade)
{}
