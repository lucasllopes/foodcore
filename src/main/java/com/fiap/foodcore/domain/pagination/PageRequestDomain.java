package com.fiap.foodcore.domain.pagination;


import java.util.List;

public record PageRequestDomain(int page, int size, List<SortOrder> sortOrders) {

    public PageRequestDomain {
        if (page < 0) {
            throw new IllegalArgumentException("page index must not be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("page size must be greater than zero");
        }
    }
}

