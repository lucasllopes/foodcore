package com.fiap.foodcore.application.usecase;

import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

public interface ListUserSubtypeUseCase {
    DomainPage<CreateUserSubtypeOutput> execute(PageRequestDomain pageRequest);
}
