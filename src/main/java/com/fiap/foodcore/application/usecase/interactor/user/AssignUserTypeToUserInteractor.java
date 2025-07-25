package com.fiap.foodcore.application.usecase.interactor.user;

import com.fiap.foodcore.application.exception.BusinessException;
import com.fiap.foodcore.application.exception.DataNotFoundException;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.application.usecase.AssignUserTypeToUserUseCase;
import com.fiap.foodcore.application.usecase.input.AssignUserSubtypeToUserInput;
import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.domain.UserTypeDomain;

public class AssignUserTypeToUserInteractor implements AssignUserTypeToUserUseCase {

    private final UserGateway userGateway;
    private final UserSubtypeGateway userSubtypeGateway;

    public AssignUserTypeToUserInteractor(UserGateway userGateway, UserSubtypeGateway userSubtypeGateway) {
        this.userGateway = userGateway;
        this.userSubtypeGateway = userSubtypeGateway;
    }

    @Override
    public CreateUserOutput execute(Long id, AssignUserSubtypeToUserInput input) {

        User existingUser = userGateway.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        if(existingUser.getTipo().equals(UserTypeDomain.DONO)){
            throw new BusinessException("Usuários com perfil 'Dono' não podem ter um subtipo atribuído.");
        }

        UserSubtype existingUserSubyype = userSubtypeGateway.findById(input.id()).orElseThrow(() -> new DataNotFoundException("Tipo de usuário não encontrado"));

        User domain = existingUser.assignUserType(existingUserSubyype);
        User savedUser = userGateway.save(domain);
        return UserMapper.fromDomain(savedUser);
    }
}
