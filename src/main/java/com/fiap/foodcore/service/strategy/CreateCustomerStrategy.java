package com.fiap.foodcore.service.strategy;


import com.fiap.foodcore.converter.UserConverter;
import com.fiap.foodcore.domain.model.User;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.exception.DuplicatedDataException;
import com.fiap.foodcore.persistence.entity.UserType;
import com.fiap.foodcore.persistence.entity.UserEntity;
import com.fiap.foodcore.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserResponseDTO create(UserCreateRequestDTO dto) {
        validarDuplicidadeParaCriacao(dto);

        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        User user = UserConverter.fromCreateDto(senhaCriptografada, UserType.CLIENTE, dto);

        UserEntity salvo = userRepository.save(UserConverter.toEntity(user));
        return UserConverter.toResponseDTO(UserConverter.toDomain(salvo));
    }

    private void validarDuplicidadeParaCriacao(UserCreateRequestDTO dto) {
        userRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new DuplicatedDataException("Email já está em uso.");
        });

        userRepository.findByLogin(dto.login()).ifPresent(u -> {
            throw new DuplicatedDataException("Login já está em uso.");
        });
    }


}
