package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.AddressEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AddressEntityMapper {

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

    public static List<Address> toDomain(List<AddressEntity> entities) {
        if (entities == null) return List.of();

        return entities.stream()
                .map(entity -> Address.rebuildAddress(
                        entity.getId(),
                        entity.getLogradouro(),
                        entity.getNumero(),
                        entity.getComplemento(),
                        entity.getBairro(),
                        entity.getCidade(),
                        entity.getEstado(),
                        entity.getCep()
                ))
                .collect(Collectors.toList());
    }
}