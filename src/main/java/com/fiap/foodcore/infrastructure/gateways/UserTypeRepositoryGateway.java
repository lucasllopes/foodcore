package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.UserTypeGateway;
import com.fiap.foodcore.domain.UserType;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserTypeRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserTypeEntity;
import com.fiap.foodcore.infrastructure.mapper.UserTypeEntityMapper;
import java.util.Optional;

public class UserTypeRepositoryGateway implements UserTypeGateway {

    private final UserTypeRepository userTypeRepository;

    public UserTypeRepositoryGateway(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public UserType save(UserType userType) {
        UserTypeEntity entity = UserTypeEntityMapper.toEntity(userType);
        UserTypeEntity userTypeCreated = userTypeRepository.save(entity);
        return UserTypeEntityMapper.toDomain(userTypeCreated);
    }

    @Override
    public Optional<UserType> findById(Long id) {
        return userTypeRepository.findById(id)
                .map(UserTypeEntityMapper::toDomain);
    }

    @Override
    public DomainPage<UserType> findAll(PageRequestDomain pageRequest) {
        return null;
    }

    @Override
    public void delete(UserType user) {

    }

    @Override
    public Optional<UserType> findByNameIgnoreCase(String name) {
        return userTypeRepository.findByNameIgnoreCase(name)
                .map(UserTypeEntityMapper::toDomain);
    }
}
