package com.fiap.foodcore.service.impl;

import com.fiap.foodcore.converter.UserConverter;
import com.fiap.foodcore.domain.model.User;
import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import com.fiap.foodcore.exception.WrongPasswordException;
import com.fiap.foodcore.persistence.entity.UserEntity;
import com.fiap.foodcore.exception.DuplicatedDataException;
import com.fiap.foodcore.exception.DataNotFoundException;
import com.fiap.foodcore.persistence.repository.UserRepository;
import com.fiap.foodcore.service.UserService;
import com.fiap.foodcore.service.strategy.CreateUserStrategyFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserStrategyFactory strategyFactory;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CreateUserStrategyFactory strategyFactory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.strategyFactory = strategyFactory;
    }

    @Override
    public UserResponseDTO findById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        User user = UserConverter.toDomain(entity);
        return UserConverter.toResponseDTO(user);
    }


    @Override
    public Page<UserResponseDTO> listPaginatedUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserConverter::toDomain)
                .map(UserConverter::toResponseDTO);
    }

    @Override
    public UserResponseDTO createUser(UserCreateRequestDTO dto) {
        var strategy = strategyFactory.getStrategy(dto.tipo());
        return strategy.create(dto);
    }


    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado."));

        User user = UserConverter.toDomain(entity);

        validateDuplicationForUpdate(dto, id);

        user.updateInformation(dto);
        UserEntity updatedUser  = userRepository.save(UserConverter.toEntity(user));

        return UserConverter.toResponseDTO(UserConverter.toDomain(updatedUser));
    }

    private void validateDuplicationForUpdate(UserUpdateRequestDTO dto, Long idAtual) {
        userRepository.findByEmail(dto.email()).ifPresent(u -> {
            if (!u.getId().equals(idAtual)) {
                throw new DuplicatedDataException("Email já está em uso por outro usuário.");
            }
        });
    }

    @Override
    public void changePassword(Long id, ChangePasswordRequestDTO dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        User user = UserConverter.toDomain(entity);

        boolean currentPasswordMatches = passwordEncoder.matches(dto.senhaAtual(), user.getSenha());
        if (!currentPasswordMatches) {
            throw new WrongPasswordException("Senha atual incorreta.");
        }

        String newEncryptedPassword = passwordEncoder.encode(dto.novaSenha());
        user.changePassword(newEncryptedPassword);

        userRepository.save(UserConverter.toEntity(user));
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado."));

        user.getEnderecos().clear();
        userRepository.save(user);

        userRepository.delete(user);
    }

}
