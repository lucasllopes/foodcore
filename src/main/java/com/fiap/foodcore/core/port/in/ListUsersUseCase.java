package com.fiap.foodcore.core.port.in;

import com.fiap.foodcore.core.model.domain.DomainPage;
import com.fiap.foodcore.core.model.domain.PageRequestDomain;
import com.fiap.foodcore.dto.UserResponseDTO;

public interface ListUsersUseCase {
    DomainPage<UserResponseDTO> execute(PageRequestDomain pageRequest);
}
