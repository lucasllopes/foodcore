package com.fiap.foodcore.application.usecase.interactor.user;

import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.application.usecase.AssignUserTypeToUserUseCase;
import com.fiap.foodcore.application.usecase.input.AssignUserTypeToUserInput;
import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserType;

public class AssignUserTypeToUserInteractor implements AssignUserTypeToUserUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public AssignUserTypeToUserInteractor(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public CreateUserOutput execute(Long id, AssignUserTypeToUserInput input) {

        User existingUser = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        UserType existingUserType = userTypeGateway.findById(input.id()).orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));

        User domain = existingUser.assignUserType(existingUserType);
        User savedUser = userGateway.save(domain);
        return UserMapper.fromDomain(savedUser);
    }
}
