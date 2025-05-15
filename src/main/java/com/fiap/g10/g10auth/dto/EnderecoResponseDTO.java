package com.fiap.g10.g10auth.dto;

public record EnderecoResponseDTO(String logradouro,
                                  String numero,
                                  String complemento,
                                  String bairro,
                                  String cep,
                                  String estado,
                                  String cidade)
{}
