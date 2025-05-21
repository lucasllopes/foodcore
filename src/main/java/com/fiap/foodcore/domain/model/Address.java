package com.fiap.foodcore.domain.model;

import com.fiap.foodcore.dto.AddressCreateRequestDTO;
import com.fiap.foodcore.dto.AddressUpdateRequestDTO;
import com.fiap.foodcore.persistence.entity.AddressEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class Address {

    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private Address() {

    }

    public static Address fromCreateRequest(AddressCreateRequestDTO dto) {
        Address e = new Address();

        e.logradouro = dto.logradouro();
        e.numero = dto.numero();
        e.complemento = dto.complemento();
        e.bairro = dto.bairro();
        e.cidade = dto.cidade();
        e.estado = dto.estado();
        e.cep = dto.cep();
        return e;
    }

    public static Address fromEntity(AddressEntity entity) {
        if (entity == null) return null;

        Address e = new Address();
        e.id = entity.getId();
        e.logradouro = entity.getLogradouro();
        e.numero = entity.getNumero();
        e.complemento = entity.getComplemento();
        e.bairro = entity.getBairro();
        e.cidade = entity.getCidade();
        e.estado = entity.getEstado();
        e.cep = entity.getCep();

        return e;
    }

    public static List<Address> fromEntity(List<AddressEntity> entities) {
        return entities.stream()
                .map(Address::fromEntity)
                .toList();
    }


    public void updateFrom(AddressUpdateRequestDTO dto) {
        this.logradouro = dto.logradouro();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.bairro = dto.bairro();
        this.cidade = dto.cidade();
        this.estado = dto.estado();
        this.cep = dto.cep();
    }
}
