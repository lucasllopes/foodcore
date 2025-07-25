package com.fiap.foodcore.domain.pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainPage<T> {

    private final List<T> items;
    private final int page;
    private final int size;
    private final long totalElements;

    public DomainPage(List<T> items, int page, int size, long totalElements) {
        this.page = page;
        this.size = size;
        if (items == null) {
            throw new IllegalArgumentException("items must not be null");
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("totalElements must not be negative");
        }
        this.items = new ArrayList<>(items);
        this.totalElements = totalElements;
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public <R> DomainPage<R> map(Function<? super T, ? extends R> mapper) {
        List<R> mappedItems = items.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new DomainPage<>(mappedItems, page, size, totalElements);
    }

}
