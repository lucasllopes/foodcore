package com.fiap.foodcore.application.usecase.interactor.usersubtype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.DeleteUserSubtypeUseCase;

public class DeleteUserSubtypeInteractor implements DeleteUserSubtypeUseCase {

    private final UserSubtypeGateway gateway;

    public DeleteUserSubtypeInteractor(UserSubtypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(Long id) {
        var user = gateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));
        gateway.delete(user);
    }
}
