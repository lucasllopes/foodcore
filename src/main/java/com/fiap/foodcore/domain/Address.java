package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.AddressUpdateInput;
import com.fiap.foodcore.application.usecase.input.CreateAddressInput;
import lombok.Builder;
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


    public static Builder builder() {
        return new Builder();
    }


    public void update(String logradouro, String numero, String complemento,
                       String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }


    public static class Builder {
        private final Address instance = new Address();

        public Builder id(Long id) {
            instance.id = id;
            return this;
        }

        public Builder logradouro(String logradouro) {
            instance.logradouro = logradouro;
            return this;
        }

        public Builder numero(String numero) {
            instance.numero = numero;
            return this;
        }

        public Builder complemento(String complemento) {
            instance.complemento = complemento;
            return this;
        }

        public Builder bairro(String bairro) {
            instance.bairro = bairro;
            return this;
        }

        public Builder cidade(String cidade) {
            instance.cidade = cidade;
            return this;
        }

        public Builder estado(String estado) {
            instance.estado = estado;
            return this;
        }

        public Builder cep(String cep) {
            instance.cep = cep;
            return this;
        }

        public Address build() {
            return instance;
        }
    }


    public static Address rebuildAddress(Long id,
                                         String logradouro,
                                         String numero,
                                         String complemento,
                                         String bairro,
                                         String cidade,
                                         String estado,
                                         String cep) {
        return Address.builder()
                .id(id)
                .logradouro(logradouro)
                .numero(numero)
                .complemento(complemento)
                .bairro(bairro)
                .cidade(cidade)
                .estado(estado)
                .cep(cep)
                .build();
    }
}