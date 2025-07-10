package com.fiap.foodcore.domain;

import com.fiap.foodcore.domain.exception.UserTypeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTypeDomainTest {

    @Test
    void deveRetornarEnumTipoCliente() {
        UserTypeDomain result = UserTypeDomain.fromString("CLIENTE");
        assertEquals(UserTypeDomain.CLIENTE, result);
    }

    @Test
    void deveRetornarEnumTipoDono() {
        UserTypeDomain result = UserTypeDomain.fromString("DONO");
        assertEquals(UserTypeDomain.DONO, result);
    }

    @Test
    void deveRetornarErroTipoInvalido() {
        assertThrows(UserTypeNotFoundException.class, () -> UserTypeDomain.fromString("CUSTOMERR"));
    }
}
