package com.fiap.g10.g10auth.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
@AttributeOverrides({
        @AttributeOverride(name = "logradouro", column = @Column(name = "endereco_logradouro")),
        @AttributeOverride(name = "numero", column = @Column(name = "endereco_numero")),
        @AttributeOverride(name = "complemento", column = @Column(name = "endereco_complemento")),
        @AttributeOverride(name = "bairro", column = @Column(name = "endereco_bairro")),
        @AttributeOverride(name = "cep", column = @Column(name = "endereco_cep"))
})
public class Endereco {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    public Endereco() {

    }

    public Endereco(String logradouro, String numero, String complemento, String bairro, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
    }
}
