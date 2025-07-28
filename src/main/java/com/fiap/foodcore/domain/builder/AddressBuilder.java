package com.fiap.foodcore.domain.builder;

import com.fiap.foodcore.domain.Address;

public class AddressBuilder {
    private Long id;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;

    public static AddressBuilder getInstance() {
        return new AddressBuilder();
    }

    private AddressBuilder() {}

    public AddressBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AddressBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder withNumber(String numero) {
        this.number = numero;
        return this;
    }

    public AddressBuilder withComplement(String complemento) {
        this.complement = complemento;
        return this;
    }

    public AddressBuilder withNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public AddressBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public AddressBuilder withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public Address build() {
        return Address.builder()
                .id(id)
                .logradouro(street)
                .numero(number)
                .complemento(complement)
                .bairro(neighborhood)
                .cidade(city)
                .estado(state)
                .cep(zipCode)
                .build();
    }
}