package com.fiap.foodcore.application.usecase.interactor.user;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.AssignUserTypeUseCase;
import com.fiap.foodcore.application.usecase.input.AssignUserSubtypeInput;

public class AssignUserTypeInteractor implements AssignUserTypeUseCase {

    private final UserGateway userGateway;
    private final UserSubtypeGateway userSubtypeGateway;

    public AssignUserTypeInteractor(UserGateway userGateway, UserSubtypeGateway userSubtypeGateway) {
        this.userGateway = userGateway;
        this.userSubtypeGateway = userSubtypeGateway;
    }

    @Override
    public void execute(Long userId, AssignUserSubtypeInput input) {
        var user = userGateway.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        var userType = userSubtypeGateway.findById(input.userTypeId())
                .orElseThrow(() -> new DataNotFoundException("Tipo do usuário não encontrado"));

        user.assignUserType(userType);
        userGateway.save(user);
    }
}
