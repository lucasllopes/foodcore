package com.fiap.foodcore.infrastructure.strategy;


import com.fiap.foodcore.application.strategy.CreateUserStrategy;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.exception.DuplicatedDataException;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;
import com.fiap.foodcore.infrastructure.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Qualifier("owner")
public class CreateOwnerStrategy implements CreateUserStrategy {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateOwnerStrategy(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserOutput create(CreateUserInput input) {
        validateDuplicateForCreation(input);

        String encryptedPassword = passwordEncoder.encode(input.senha());
        User user = UserMapper.fromCreateInput(encryptedPassword, UserTypeDomain.DONO, input);

        UserEntity salvo = userRepository.save(UserEntityMapper.toEntity(user));
        return UserMapper.fromDomain(UserEntityMapper.toDomain(salvo));

    }

    private void validateDuplicateForCreation(CreateUserInput createUserInput) {
        userRepository.findByEmail(createUserInput.email()).ifPresent(u -> {
            throw new DuplicatedDataException("Email já está em uso.");
        });

        userRepository.findByLogin(createUserInput.login()).ifPresent(u -> {
            throw new DuplicatedDataException("Login já está em uso.");
        });
    }
}

