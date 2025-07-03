package com.fiap.foodcore.application.service.strategy;


import com.fiap.foodcore.infrastructure.gateways.mapper.UserDtoMapper;
import com.fiap.foodcore.infrastructure.gateways.mapper.UserMapper;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserResponseDTO;
import com.fiap.foodcore.exception.DuplicatedDataException;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserType;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;
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
    public UserResponseDTO create(UserCreateRequestDTO dto) {
        validateDuplicateForCreation(dto);

        String encryptedPassword = passwordEncoder.encode(dto.senha());
        User user = UserDtoMapper.fromCreateDto(encryptedPassword, UserType.DONO, dto);

        UserEntity salvo = userRepository.save(UserMapper.toEntity(user));
        return UserDtoMapper.toResponseDto(
                UserMapper.toDomain(salvo)
        );
    }

    private void validateDuplicateForCreation(UserCreateRequestDTO dto) {
        userRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new DuplicatedDataException("Email já está em uso.");
        });

        userRepository.findByLogin(dto.login()).ifPresent(u -> {
            throw new DuplicatedDataException("Login já está em uso.");
        });
    }

}

