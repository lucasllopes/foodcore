package com.fiap.foodcore.application.usecase.interactor.usertype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.DeleteUserTypeUseCase;

public class DeleteUserTypeInteractor implements DeleteUserTypeUseCase {

    private final UserTypeGateway gateway;

    public DeleteUserTypeInteractor(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(Long id) {
        var user = gateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));
        gateway.delete(user);
    }
}
