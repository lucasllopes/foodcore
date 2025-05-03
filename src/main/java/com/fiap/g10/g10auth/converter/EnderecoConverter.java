package com.fiap.g10.g10auth.converter;

import com.fiap.g10.g10auth.domain.model.Endereco;
import com.fiap.g10.g10auth.persistence.entity.EnderecoEntity;

public class EnderecoConverter {

    public static EnderecoEntity toEntity(Endereco endereco) {
        if (endereco == null) return null;

        EnderecoEntity entity = new EnderecoEntity();
        entity.setId(endereco.getId());
        entity.setLogradouro(endereco.getLogradouro());
        entity.setNumero(endereco.getNumero());
        entity.setComplemento(endereco.getComplemento());
        entity.setBairro(endereco.getBairro());
        entity.setCidade(endereco.getCidade());
        entity.setEstado(endereco.getEstado());
        entity.setCep(endereco.getCep());
        return entity;
    }
}