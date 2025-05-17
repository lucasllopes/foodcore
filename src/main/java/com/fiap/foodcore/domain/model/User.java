package com.fiap.foodcore.domain.model;

import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import com.fiap.foodcore.persistence.entity.UserType;
import com.fiap.foodcore.persistence.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class User {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataUltimaAlteracao;
    private UserType tipo;
    private List<Address> address;

    private User() {

    }

    public static User novoUsuario(String senhaCodificada, UserType tipo, UserCreateRequestDTO dto) {
        User user = new User();
        user.nome = dto.nome();
        user.email = dto.email();
        user.login = dto.login();
        user.senha = senhaCodificada;
        user.tipo = tipo;

        if (!dto.enderecos().isEmpty()) {
            List<Address> addresses = dto.enderecos()
                    .stream()
                    .map(Address::novoEndereco)
                    .toList();

            user.address = addresses;
        }


        user.dataUltimaAlteracao = LocalDateTime.now();
        return user;
    }


    public void trocarSenha(String novaSenha) {
        this.senha = novaSenha;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    public void atualizarDados(UserUpdateRequestDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.tipo = dto.tipo();

        if (this.address != null && dto.enderecos() != null) {
            int limite = Math.min(this.address.size(), dto.enderecos().size());
            for (int i = 0; i < limite; i++) {
                this.address.get(i).atualizarDados(dto.enderecos().get(i));
            }
        }

        this.dataUltimaAlteracao = LocalDateTime.now();
    }


    public static User reconstruirUsuario(UserEntity entity) {
        User user = new User();
        user.id = entity.getId();
        user.nome = entity.getNome();
        user.email = entity.getEmail();
        user.login = entity.getLogin();
        user.senha = entity.getSenha();
        user.tipo = entity.getTipo();

        user.address = Address.reconstruirEndereco(entity.getEnderecos());

        user.dataUltimaAlteracao = entity.getDataUltimaAlteracao();
        return user;
    }

    public static User reconstruirUsuarioToken(UserEntity entity) {
        User user = new User();
        user.id = entity.getId();
        user.nome = entity.getNome();
        user.email = entity.getEmail();
        user.login = entity.getLogin();
        user.senha = entity.getSenha();
        user.tipo = entity.getTipo();
        user.dataUltimaAlteracao = entity.getDataUltimaAlteracao();
        return user;

    }
}

