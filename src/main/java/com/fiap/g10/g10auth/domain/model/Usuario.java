package com.fiap.g10.g10auth.domain.model;

import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioUpdateRequestDTO;
import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataUltimaAlteracao;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private TipoUsuario tipo;

    public Usuario() {
    }

    public static Usuario novoUsuario(Long id, String senhaCodificada, UsuarioCreateRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.id = id;
        usuario.nome = dto.nome();
        usuario.email = dto.email();
        usuario.login = dto.login();
        usuario.senha = senhaCodificada;
        usuario.tipo = dto.tipo();
        usuario.logradouro = dto.logradouro();
        usuario.numero = dto.numero();
        usuario.complemento = dto.complemento();
        usuario.bairro = dto.bairro();
        usuario.cep = dto.cep();
        usuario.cidade = dto.cidade();
        usuario.estado = dto.estado();
        usuario.dataUltimaAlteracao = LocalDateTime.now();
        return usuario;
    }


    public void trocarSenha(String novaSenha) {
        this.senha = novaSenha;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    public void atualizarDados(UsuarioUpdateRequestDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.tipo = dto.tipo();
        this.logradouro = dto.logradouro();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.bairro = dto.bairro();
        this.cep = dto.cep();
        this.cidade = dto.cidade();
        this.estado = dto.estado();
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    public static Usuario reconstruirUsuario(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.id = entity.getId();
        usuario.nome = entity.getNome();
        usuario.email = entity.getEmail();
        usuario.login = entity.getLogin();
        usuario.senha = entity.getSenha();
        usuario.tipo = entity.getTipo();
        usuario.logradouro = entity.getLogradouro();
        usuario.numero = entity.getNumero();
        usuario.complemento = entity.getComplemento();
        usuario.bairro = entity.getBairro();
        usuario.cep = entity.getCep();
        usuario.cidade = entity.getCidade();
        usuario.estado = entity.getEstado();
        usuario.dataUltimaAlteracao = entity.getDataUltimaAlteracao();
        return usuario;
    }
}
