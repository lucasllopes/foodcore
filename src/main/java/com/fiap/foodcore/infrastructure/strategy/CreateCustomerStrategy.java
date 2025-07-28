package com.fiap.foodcore.infrastructure.strategy;


import com.fiap.foodcore.application.strategy.CreateUserStrategy;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.Address;
import com.fiap.foodcore.infrastructure.mapper.UserEntityMapper;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.application.exception.DuplicatedDataException;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Qualifier("customer")
public class CreateCustomerStrategy implements CreateUserStrategy {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCustomerStrategy(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CreateUserOutput create(CreateUserInput input) {
        validateDuplicateForCreation(input);

        String encryptedPassword = passwordEncoder.encode(input.senha());

        User user = User.builder()
                .nome(input.nome())
                .email(input.email())
                .login(input.login())
                .senha(encryptedPassword)
                .tipo(input.tipo())
                .address(input.enderecos().stream()
                        .map(endereco -> Address.builder()
                                .logradouro(endereco.logradouro())
                                .numero(endereco.numero())
                                .complemento(endereco.complemento())
                                .bairro(endereco.bairro())
                                .cidade(endereco.cidade())
                                .estado(endereco.estado())
                                .cep(endereco.cep())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        UserEntity salvo = userRepository.save(UserEntityMapper.toEntity(user));
        return UserMapper.fromDomain(UserEntityMapper.toDomain(salvo));
    }

    private void validateDuplicateForCreation(CreateUserInput input) {
        userRepository.findByEmail(input.email()).ifPresent(u -> {
            throw new DuplicatedDataException("Email já está em uso.");
        });

        userRepository.findByLogin(input.login()).ifPresent(u -> {
            throw new DuplicatedDataException("Login já está em uso.");
        });
    }
}
