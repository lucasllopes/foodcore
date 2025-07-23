package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserSubtypeRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserSubtypeEntity;
import com.fiap.foodcore.infrastructure.mapper.UserTypeEntityMapper;

import java.util.List;
import java.util.Optional;

public class UserTypeRepositoryGateway implements UserSubtypeGateway {

    private final UserSubtypeRepository userSubtypeRepository;

    public UserTypeRepositoryGateway(UserSubtypeRepository userSubtypeRepository) {
        this.userSubtypeRepository = userSubtypeRepository;
    }

    @Override
    public UserSubtype save(UserSubtype userType) {
        UserSubtypeEntity entity = UserTypeEntityMapper.toEntity(userType);
        UserSubtypeEntity userTypeCreated = userSubtypeRepository.save(entity);
        return UserTypeEntityMapper.toDomain(userTypeCreated);
    }

    @Override
    public Optional<UserSubtype> findById(Long id) {
        return userSubtypeRepository.findById(id)
                .map(UserTypeEntityMapper::toDomain);
    }

    @Override
    public DomainPage<UserSubtype> findAll(PageRequestDomain pageRequest) {
        return null;
    }

    @Override
    public void delete(UserSubtype user) {
        UserSubtypeEntity entity = UserTypeEntityMapper.toEntity(user);
        userSubtypeRepository.delete(entity);
    }

    @Override
    public List<UserSubtype> findByNameIgnoreCase(String name) {
        return userSubtypeRepository.findByNameIgnoreCase(name).stream()
                .map(UserTypeEntityMapper::toDomain)
                .toList();
    }
}
