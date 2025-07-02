package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.infrastructure.gateways.mapper.UserMapper;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.domain.DomainPage;
import com.fiap.foodcore.domain.PageRequestDomain;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;

import java.util.Optional;

public class UserRepositoryGateway implements UserGateway {

    private final UserRepository userRepository;

    public UserRepositoryGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public DomainPage<User> findAll(PageRequestDomain pageRequest) {
        var springPage = userRepository.findAll(
                org.springframework.data.domain.PageRequest
                        .of(pageRequest.page(), pageRequest.size())
        );
        var items = springPage.getContent().stream()
                .map(UserMapper::toDomain)
                .toList();
        return new DomainPage<>(items, springPage.getTotalElements());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var entity = UserMapper.toEntity(user);
        var saved = userRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public void delete(User user) {
        var entity = UserMapper.toEntity(user);
        userRepository.delete(entity);
    }
}
