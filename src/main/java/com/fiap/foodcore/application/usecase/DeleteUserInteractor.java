package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.exception.DataNotFoundException;

public class DeleteUserInteractor {

    private final UserGateway userGateway;

    public DeleteUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(Long id) {
        var user = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
        userGateway.delete(user);
    }
}
