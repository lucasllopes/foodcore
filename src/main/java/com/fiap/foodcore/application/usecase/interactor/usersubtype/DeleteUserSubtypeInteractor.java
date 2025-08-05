package com.fiap.foodcore.application.usecase.interactor.usersubtype;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.DeleteUserSubtypeUseCase;
import org.springframework.dao.DataIntegrityViolationException;

public class DeleteUserSubtypeInteractor implements DeleteUserSubtypeUseCase {

    private final UserSubtypeGateway gateway;
    private final UserGateway userGateway;

    public DeleteUserSubtypeInteractor(UserSubtypeGateway gateway, UserGateway userGateway) {
        this.gateway = gateway;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(Long id) {
        var user = gateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Subtipo de usuário não encontrado"));

        validateUserSubType(id);
        gateway.delete(user);
    }

    private void validateUserSubType(Long id) {
        if (this.userGateway.existsByUserSubType(id)) {
            throw new DataIntegrityViolationException("O subtipo de usuário informado está vinculado a um ou mais usuários e não pode ser excluído.");
        }
    }
}
