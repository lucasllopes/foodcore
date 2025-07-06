package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.PasswordEncryptionGateway;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.usecase.input.ChangePasswordInput;
import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.exception.WrongPasswordException;

public class ChangePasswordInteractor {

    private final UserGateway userGateway;
    private final PasswordEncryptionGateway passwordEncryptionGateway;

    public ChangePasswordInteractor(UserGateway userGateway,
                                    PasswordEncryptionGateway passwordEncryptionGateway) {
        this.userGateway = userGateway;
        this.passwordEncryptionGateway = passwordEncryptionGateway;
    }

    public void execute(Long id, ChangePasswordInput input) {
        var user = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        if (!passwordEncryptionGateway.matches(input.senhaAtual(), user.getSenha())) {
            throw new WrongPasswordException("Senha atual incorreta");
        }

        user.changePassword(passwordEncryptionGateway.encode(input.novaSenha()));
        userGateway.save(user);
    }
}