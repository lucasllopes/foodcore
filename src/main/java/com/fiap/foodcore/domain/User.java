package com.fiap.foodcore.domain;

import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class User {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataUltimaAlteracao;
    private UserTypeDomain tipo;
    private List<Address> address;
    private UserSubtype userSubtype;

    private User() {
    }

    // Método para alterar senha
    public void changePassword(String novaSenha) {
        this.senha = novaSenha;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    // Método para atualizar informações do usuário
    public void updateInformation(String nome, String email, List<Address> enderecos) {
        this.nome = nome;
        this.email = email;

        if (enderecos != null) {
            if (this.address == null) {
                this.address = new ArrayList<>();
            }

            this.address = new ArrayList<>(enderecos);
        }

        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    // Método para atribuir subtipo ao usuário
    public User assignUserType(UserSubtype existingUserType) {
        this.userSubtype = existingUserType;
        this.dataUltimaAlteracao = LocalDateTime.now();
        return this;
    }

    // Método para criar um builder
    public static Builder builder() {
        return new Builder();
    }

    // Classe Builder interna
    public static class Builder {
        private final User instance = new User();

        public Builder id(Long id) {
            instance.id = id;
            return this;
        }

        public Builder nome(String nome) {
            instance.nome = nome;
            return this;
        }

        public Builder email(String email) {
            instance.email = email;
            return this;
        }

        public Builder login(String login) {
            instance.login = login;
            return this;
        }

        public Builder senha(String senha) {
            instance.senha = senha;
            return this;
        }

        public Builder dataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
            instance.dataUltimaAlteracao = dataUltimaAlteracao;
            return this;
        }

        public Builder tipo(UserTypeDomain tipo) {
            instance.tipo = tipo;
            return this;
        }

        public Builder address(List<Address> address) {
            instance.address = address;
            return this;
        }

        public Builder userSubtype(UserSubtype userSubtype) {
            instance.userSubtype = userSubtype;
            return this;
        }

        public User build() {
            if (instance.dataUltimaAlteracao == null) {
                instance.dataUltimaAlteracao = LocalDateTime.now();
            }
            return instance;
        }
    }

    // Métodos estáticos de utilidade para facilitar a migração do código existente

    public static User create(String senhaCodificada, UserTypeDomain tipo, String nome, String email, String login, List<Address> enderecos) {
        return User.builder()
                .nome(nome)
                .email(email)
                .login(login)
                .senha(senhaCodificada)
                .tipo(tipo)
                .address(enderecos)
                .build();
    }

    public static User rebuildUser(
            Long id,
            String nome,
            String email,
            String login,
            String senha,
            UserTypeDomain tipo,
            List<Address> enderecos,
            LocalDateTime dataUltimaAlteracao
    ) {
        return User.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .login(login)
                .senha(senha)
                .tipo(tipo)
                .address(enderecos)
                .dataUltimaAlteracao(dataUltimaAlteracao)
                .build();
    }

    public static User rebuildUserWithType(
            Long id,
            String nome,
            String email,
            String login,
            String senha,
            UserTypeDomain tipo,
            List<Address> enderecos,
            LocalDateTime dataUltimaAlteracao,
            UserSubtype userType
    ) {
        return User.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .login(login)
                .senha(senha)
                .tipo(tipo)
                .address(enderecos)
                .dataUltimaAlteracao(dataUltimaAlteracao)
                .userSubtype(userType)
                .build();
    }

    public static User rebuildForAuthentication(
            Long id,
            String login,
            String senha,
            UserTypeDomain tipo
    ) {
        return User.builder()
                .id(id)
                .login(login)
                .senha(senha)
                .tipo(tipo)
                .build();
    }
}

