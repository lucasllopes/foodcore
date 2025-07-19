package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

public interface ListUserUseCase {
    DomainPage<CreateUserOutput> execute(PageRequestDomain pageRequest);
}
