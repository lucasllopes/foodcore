package com.fiap.foodcore.adapter.out.persistence.jpa;

import com.fiap.foodcore.adapter.out.persistence.mapper.UserMapper;
import com.fiap.foodcore.core.model.domain.DomainPage;
import com.fiap.foodcore.core.model.domain.PageRequestDomain;
import com.fiap.foodcore.core.model.domain.User;
import com.fiap.foodcore.core.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGatewayImpl implements UserRepositoryPort {

    private final UserRepository userRepository;

    public UserGatewayImpl(UserRepository userRepository) {
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
