package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.AddressUpdateInput;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import lombok.Getter;

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

    public static Address addAddress(CreateAddressInput input) {
        Address e = new Address();

        e.logradouro = input.logradouro();
        e.numero = input.numero();
        e.complemento = input.complemento();
        e.bairro = input.bairro();
        e.cidade = input.cidade();
        e.estado = input.estado();
        e.cep = input.cep();
        return e;
    }

    public static Address addAddress(AddressUpdateInput input) {
        Address e = new Address();
        e.logradouro = input.logradouro();
        e.numero = input.numero();
        e.complemento = input.complemento();
        e.bairro = input.bairro();
        e.cidade = input.cidade();
        e.estado = input.estado();
        e.cep = input.cep();
        return e;
    }

    public void updateFrom(AddressUpdateInput input) {
        this.logradouro = input.logradouro();
        this.numero = input.numero();
        this.complemento = input.complemento();
        this.bairro = input.bairro();
        this.cidade = input.cidade();
        this.estado = input.estado();
        this.cep = input.cep();
    }

    public static Address rebuildAddress(Long id,
                                         String logradouro,
                                         String numero,
                                         String complemento,
                                         String bairro,
                                         String cidade,
                                         String estado,
                                         String cep) {

        Address e = new Address();
        e.id = id;
        e.logradouro = logradouro;
        e.numero = numero;
        e.complemento = complemento;
        e.bairro = bairro;
        e.cidade = cidade;
        e.estado = estado;
        e.cep = cep;
        return e;
    }
}
