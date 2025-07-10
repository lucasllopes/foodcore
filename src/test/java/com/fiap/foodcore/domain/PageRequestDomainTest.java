package com.fiap.foodcore.domain;

import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PageRequestDomainTest {

    @Test
    void deveLancarExcecaoQuandoPageForNegativo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new PageRequestDomain(-1, 10);
        });
        assertEquals("page index must not be negative", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoSizeForNegativo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new PageRequestDomain(0, 0);
        });
        assertEquals("page size must be greater than zero", exception.getMessage());
    }
}
