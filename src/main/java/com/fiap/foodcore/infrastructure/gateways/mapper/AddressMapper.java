package com.fiap.foodcore.infrastructure.gateways.mapper;

import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.AddressEntity;

public class AddressMapper {

    public static AddressEntity toEntity(Address address) {
        if (address == null) return null;

        AddressEntity entity = new AddressEntity();
        entity.setId(address.getId());
        entity.setLogradouro(address.getLogradouro());
        entity.setNumero(address.getNumero());
        entity.setComplemento(address.getComplemento());
        entity.setBairro(address.getBairro());
        entity.setCidade(address.getCidade());
        entity.setEstado(address.getEstado());
        entity.setCep(address.getCep());
        return entity;
    }
}