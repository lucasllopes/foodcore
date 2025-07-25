package com.fiap.foodcore.application.usecase.restaurant;

import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;

public interface ListRestaurantUseCase {
    DomainPage<CreateRestaurantOutput> execute(String name, PageRequestDomain pageRequest);
}
