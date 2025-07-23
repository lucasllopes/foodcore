package com.fiap.foodcore.domain;

import com.fiap.foodcore.domain.exception.UserSubtypeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
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
        assertThrows(UserSubtypeNotFoundException.class, () -> UserTypeDomain.fromString("CUSTOMERR"));
    }
}
