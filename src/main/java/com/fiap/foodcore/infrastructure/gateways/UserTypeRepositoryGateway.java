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

    private final UserTypeRepository restaurantRepository;

    public UserTypeRepositoryGateway(UserTypeRepository userTypeRepository) {
        this.restaurantRepository = userTypeRepository;
    }

    @Override
    public UserType save(UserType userType) {
        UserTypeEntity entity = UserTypeEntityMapper.toEntity(userType);
        UserTypeEntity userTypeCreated = restaurantRepository.save(entity);
        return UserTypeEntityMapper.toDomain(userTypeCreated);
    }

    @Override
    public Optional<UserType> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DomainPage<UserType> findAll(PageRequestDomain pageRequest) {
        return null;
    }

    @Override
    public void delete(UserType user) {

    }
}
