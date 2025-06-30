package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.adapter.in.web.mapper.UserDtoMapper;
import com.fiap.foodcore.core.model.domain.DomainPage;
import com.fiap.foodcore.core.model.domain.PageRequestDomain;
import com.fiap.foodcore.core.model.domain.User;
import com.fiap.foodcore.core.port.in.ListUsersUseCase;
import com.fiap.foodcore.core.port.out.UserRepositoryPort;
import com.fiap.foodcore.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ListUserInteractor implements ListUsersUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public ListUserInteractor(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public DomainPage<UserResponseDTO> execute(PageRequestDomain pageRequest) {
        DomainPage<User> domainPage =
                userRepositoryPort.findAll(pageRequest);

        return domainPage.map(UserDtoMapper::toResponseDto);
    }

}
