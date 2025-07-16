package com.fiap.foodcore.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTypeTest {

    @Test
    void shouldCreateUserTypeWithNameOnly() {
        UserType userType = UserType.create("owner");

        assertNotNull(userType);
        assertNull(userType.getId());
        assertEquals("owner", userType.getName());
    }

    @Test
    void shouldReconstructUserTypeWithIdAndName() {
        UserType userType = UserType.reconstruct(1L, "owner");

        assertNotNull(userType);
        assertEquals(1L, userType.getId());
        assertEquals("owner", userType.getName());
    }

    @Test
    void shouldUpdateUserTypeName() {
        UserType userType = UserType.create("OldName");
        userType.update("NewName");

        assertEquals("NewName", userType.getName());
    }
}
