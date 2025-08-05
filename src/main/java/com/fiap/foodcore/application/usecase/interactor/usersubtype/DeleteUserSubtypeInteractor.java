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
        validateSubTypeUser(id);
        var user = gateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Subtipo de usuário não encontrado"));
        gateway.delete(user);
    }
    private void validateSubTypeUser(Long id) {
        if (this.userGateway.existsBySubType(id)) {
            throw new DataIntegrityViolationException("ID do subtipo ja esta vinculado a um usuário");
        }
    }
}
