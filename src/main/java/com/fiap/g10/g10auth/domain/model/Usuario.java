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
    private Endereco endereco;
    private TipoUsuario tipo;

    private Usuario() {

    }

    public static Usuario novoUsuario(Long id, String senhaCodificada, UsuarioCreateRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.id = id;
        usuario.nome = dto.nome();
        usuario.email = dto.email();
        usuario.login = dto.login();
        usuario.senha = senhaCodificada;
        usuario.tipo = dto.tipo();
        usuario.endereco = Endereco.novoEndereco(dto);
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

        if (this.endereco != null) {
            this.endereco.atualizarDados(dto);
        }

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
        usuario.endereco = Endereco.reconstruirEndereco(entity.getEndereco());
        usuario.dataUltimaAlteracao = entity.getDataUltimaAlteracao();
        return usuario;
    }

}
