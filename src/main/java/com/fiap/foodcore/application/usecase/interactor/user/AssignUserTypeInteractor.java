package com.fiap.foodcore.application.usecase.interactor.user;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.AssignUserTypeUseCase;
import com.fiap.foodcore.application.usecase.input.AssignUserTypeInput;

public class AssignUserTypeInteractor implements AssignUserTypeUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public AssignUserTypeInteractor(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void execute(Long userId, AssignUserTypeInput input) {
        var user = userGateway.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        var userType = userTypeGateway.findById(input.userTypeId())
                .orElseThrow(() -> new DataNotFoundException("Tipo do usuário não encontrado"));

        user.assignUserType(userType);
        userGateway.save(user);
    }
}
