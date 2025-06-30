package com.fiap.foodcore.application.service.strategy;


import com.fiap.foodcore.adapter.in.web.mapper.UserDtoMapper;
import com.fiap.foodcore.adapter.out.persistence.mapper.UserMapper;
import com.fiap.foodcore.core.model.domain.User;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.exception.DuplicatedDataException;
import com.fiap.foodcore.adapter.out.persistence.entity.UserType;
import com.fiap.foodcore.adapter.out.persistence.entity.UserEntity;
import com.fiap.foodcore.adapter.out.persistence.jpa.UserRepository;
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
        validateDuplicateForCreation(dto);

        String encryptedPassword = passwordEncoder.encode(dto.senha());
        User user = UserDtoMapper.fromCreateDto(encryptedPassword, UserType.CLIENTE, dto);

        UserEntity salvo = userRepository.save(UserMapper.toEntity(user));
        return UserDtoMapper.toResponseDto(UserMapper.toDomain(salvo));
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
