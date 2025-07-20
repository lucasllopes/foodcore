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
    private UserType userType;

    private User() {

    }

    public static User create(String senhaCodificada, UserTypeDomain tipo, CreateUserInput createUserInput) {
        User user = new User();
        user.nome = createUserInput.nome();
        user.email = createUserInput.email();
        user.login = createUserInput.login();
        user.senha = senhaCodificada;
        user.tipo = tipo;

        if (!createUserInput.enderecos().isEmpty()) {

            user.address = createUserInput.enderecos()
                    .stream()
                    .map(Address::addAddress)
                    .toList();
        }

        user.dataUltimaAlteracao = LocalDateTime.now();
        return user;
    }


    public void changePassword(String novaSenha) {
        this.senha = novaSenha;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    public void updateInformation(UpdateUserInput input) {
        this.nome = input.nome();
        this.email = input.email();

        if (input.enderecos() != null) {
            if (this.address == null) {
                this.address = new ArrayList<>();
            }

            List<Address> updatedAddresses = new ArrayList<>();

            for (int i = 0; i < input.enderecos().size(); i++) {
                if (i < this.address.size()) {
                    Address existingAddress = this.address.get(i);
                    existingAddress.updateFrom(input.enderecos().get(i));
                    updatedAddresses.add(existingAddress);
                } else {
                    updatedAddresses.add(Address.addAddress(input.enderecos().get(i)));
                }
            }

            this.address = updatedAddresses;
        }

        this.dataUltimaAlteracao = LocalDateTime.now();
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
        User user = new User();
        user.id = id;
        user.nome = nome;
        user.email = email;
        user.login = login;
        user.senha = senha;
        user.tipo = tipo;
        user.address = enderecos;
        user.dataUltimaAlteracao = dataUltimaAlteracao;
        return user;
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
            UserType userType
    ) {
        User user = new User();
        user.id = id;
        user.nome = nome;
        user.email = email;
        user.login = login;
        user.senha = senha;
        user.tipo = tipo;
        user.address = enderecos;
        user.dataUltimaAlteracao = dataUltimaAlteracao;
        user.userType = userType;
        return user;
    }

    public static User rebuildForAuthentication(
            Long id,
            String login,
            String senha,
            UserTypeDomain tipo
    ) {
        User user = new User();
        user.id = id;
        user.login = login;
        user.senha = senha;
        user.tipo = tipo;
        return user;
    }

    public User assignUserType(UserType existingUserType) {
        this.userType = existingUserType;
        this.dataUltimaAlteracao = LocalDateTime.now();
        return this;
    }
}

