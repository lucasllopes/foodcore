package com.fiap.foodcore.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserTypeTest {

    @Test
    void shouldCreateUserTypeWithNameOnly() {
        UserSubtype userType = UserSubtype.create("owner");

        assertNotNull(userType);
        assertNull(userType.getId());
        assertEquals("owner", userType.getName());
    }

    @Test
    void shouldReconstructUserTypeWithIdAndName() {
        UserSubtype userType = UserSubtype.reconstruct(1L, "owner", LocalDateTime.now());

        assertNotNull(userType);
        assertEquals(1L, userType.getId());
        assertEquals("owner", userType.getName());
    }

    @Test
    void shouldUpdateUserTypeName() {
        UserSubtype userType = UserSubtype.create("OldName");
        userType.update("NewName");

        assertEquals("NewName", userType.getName());
    }

    @Test
    void shouldUpdateLastModifiedOnUpdate() {
        UserSubtype userType = UserSubtype.create("owner");
        userType.update("customer");

        assertNotNull(userType.getLastModified());
        assertEquals("customer", userType.getName());
    }
}
