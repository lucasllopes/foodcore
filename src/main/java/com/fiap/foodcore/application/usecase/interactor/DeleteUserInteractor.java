package com.fiap.foodcore.application.usecase.interactor;

import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.usecase.DeleteUserUseCase;

public class DeleteUserInteractor implements DeleteUserUseCase {

    private final UserGateway userGateway;

    public DeleteUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void execute(Long id) {
        var user = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
        userGateway.delete(user);
    }
}
