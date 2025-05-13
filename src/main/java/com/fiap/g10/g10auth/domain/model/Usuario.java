package com.fiap.g10.g10auth.domain.model;

import com.fiap.g10.g10auth.dto.EnderecoResponseDTO;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioUpdateRequestDTO;
import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataUltimaAlteracao;
    private TipoUsuario tipo;
    private List<Endereco> endereco;

    private Usuario() {

    }

    public static Usuario novoUsuario(String senhaCodificada, UsuarioCreateRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.nome = dto.nome();
        usuario.email = dto.email();
        usuario.login = dto.login();
        usuario.senha = senhaCodificada;
        usuario.tipo = dto.tipo();

        if (dto.enderecos() != null && !dto.enderecos().isEmpty()) {
            List<Endereco> enderecos = dto.enderecos()
                    .stream()
                    .map(Endereco::novoEndereco)
                    .toList();

            usuario.endereco = enderecos;
        }


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

        if (this.endereco != null && dto.enderecos() != null) {
            int limite = Math.min(this.endereco.size(), dto.enderecos().size());
            for (int i = 0; i < limite; i++) {
                this.endereco.get(i).atualizarDados(dto.enderecos().get(i));
            }
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

        usuario.endereco = Endereco.reconstruirEndereco(entity.getEnderecos());

        usuario.dataUltimaAlteracao = entity.getDataUltimaAlteracao();
        return usuario;
    }
}

