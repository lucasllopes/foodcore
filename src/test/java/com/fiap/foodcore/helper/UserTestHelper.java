package com.fiap.foodcore.helper;

import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserTypeDomain;
import java.util.List;

public class UserTestHelper {

    public static User getUserWithDefaultId() {

        Long id = 1L;
        return User.rebuildUser(id, "User", "user@email.com", "user", "password",
                UserTypeDomain.DONO, List.of(), null);

    }

    public static User getUserWithIdParametrized(Long id) {

        return User.rebuildUser(id, "User", "user@email.com", "user", "password",
                UserTypeDomain.DONO, List.of(), null);

    }
}
