package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.infrastructure.gateways.mapper.UserDtoMapper;
import com.fiap.foodcore.domain.DomainPage;
import com.fiap.foodcore.domain.PageRequestDomain;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;

public class ListUserInteractor {

    private final UserGateway userGateway;

    public ListUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public DomainPage<UserResponseDTO> execute(PageRequestDomain pageRequest) {
        DomainPage<User> domainPage =
                userGateway.findAll(pageRequest);

        return domainPage.map(UserDtoMapper::toResponseDto);
    }

}
