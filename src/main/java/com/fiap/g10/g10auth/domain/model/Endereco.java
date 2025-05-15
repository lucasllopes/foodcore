package com.fiap.g10.g10auth.domain.model;

import com.fiap.g10.g10auth.dto.EnderecoCreateRequestDTO;
import com.fiap.g10.g10auth.dto.EnderecoUpdateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioUpdateRequestDTO;
import com.fiap.g10.g10auth.persistence.entity.EnderecoEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class Endereco {

    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private Endereco() {

    }

    public static Endereco novoEndereco(EnderecoCreateRequestDTO dto) {
        Endereco e = new Endereco();

        e.logradouro = dto.logradouro();
        e.numero = dto.numero();
        e.complemento = dto.complemento();
        e.bairro = dto.bairro();
        e.cidade = dto.cidade();
        e.estado = dto.estado();
        e.cep = dto.cep();
        return e;
    }

    public static Endereco reconstruirEndereco(EnderecoEntity entity) {
        if (entity == null) return null;

        Endereco e = new Endereco();
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

    public static List<Endereco> reconstruirEndereco(List<EnderecoEntity> entities) {
        return entities.stream()
                .map(Endereco::reconstruirEndereco)
                .toList();
    }


    public void atualizarDados(EnderecoUpdateRequestDTO dto) {
        this.logradouro = dto.logradouro();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.bairro = dto.bairro();
        this.cidade = dto.cidade();
        this.estado = dto.estado();
        this.cep = dto.cep();
    }
}
