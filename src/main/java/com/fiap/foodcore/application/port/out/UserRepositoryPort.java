package com.fiap.foodcore.application.port.out;

import com.fiap.foodcore.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findById(Long id);
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);

}