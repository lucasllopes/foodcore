package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.UserSubtypeGateway;
import com.fiap.foodcore.domain.UserSubtype;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserSubtypeRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserSubtypeEntity;
import com.fiap.foodcore.infrastructure.mapper.UserSubtypeEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class UserSubtypeRepositoryGateway implements UserSubtypeGateway {

    private final UserSubtypeRepository userSubtypeRepository;

    public UserSubtypeRepositoryGateway(UserSubtypeRepository userSubtypeRepository) {
        this.userSubtypeRepository = userSubtypeRepository;
    }

    @Override
    public UserSubtype save(UserSubtype userType) {
        UserSubtypeEntity entity = UserSubtypeEntityMapper.toEntity(userType);
        UserSubtypeEntity userTypeCreated = userSubtypeRepository.save(entity);
        return UserSubtypeEntityMapper.toDomain(userTypeCreated);
    }

    @Override
    public Optional<UserSubtype> findById(Long id) {
        return userSubtypeRepository.findById(id)
                .map(UserSubtypeEntityMapper::toDomain);
    }


    @Override
    public void delete(UserSubtype user) {
        UserSubtypeEntity entity = UserSubtypeEntityMapper.toEntity(user);
        userSubtypeRepository.delete(entity);
    }

    @Override
    public DomainPage<UserSubtype> findAllPage(PageRequestDomain pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.page(), pageRequest.size(), toSpringSort(pageRequest.sortOrders()));
        Page<UserSubtypeEntity> paginaSpring = userSubtypeRepository.findAll(pageable);

        var items = paginaSpring.getContent().stream()
                .map(UserSubtypeEntityMapper::toDomain)
                .toList();

        return new DomainPage<>(
                items,
                paginaSpring.getNumber(),
                paginaSpring.getSize(),
                paginaSpring.getTotalElements()
        );
    }

    @Override
    public List<UserSubtype> findByNameIgnoreCase(String name) {
        return userSubtypeRepository.findByNameIgnoreCase(name).stream()
                .map(UserSubtypeEntityMapper::toDomain)
                .toList();
    }


    private Sort toSpringSort(List<SortOrder> orders) {
        return Sort.by(
                orders.stream().map(
                        order -> order.isAscending() ?
                                Sort.Order.asc(order.getProperty()) : Sort.Order.desc(order.getProperty())
                ).toList());
    }
}
