package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.mapper.UserEntityMapper;
import com.fiap.foodcore.application.gateway.UserGateway;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.infrastructure.gateways.persistence.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class UserRepositoryGateway implements UserGateway {

    private final UserRepository userRepository;

    public UserRepositoryGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(UserEntityMapper::toDomain);
    }


    @Override
    public DomainPage<User> findAllPage(PageRequestDomain pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.page(), pageRequest.size(), toSpringSort(pageRequest.sortOrders()));
        Page<UserEntity> paginaSpring = userRepository.findAll(pageable);

        var items = paginaSpring.getContent().stream()
                .map(UserEntityMapper::toDomain)
                .toList();

        return new DomainPage<>(
                items,
                paginaSpring.getNumber(),
                paginaSpring.getSize(),
                paginaSpring.getTotalElements()
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var entity = UserEntityMapper.toEntity(user);
        var saved = userRepository.save(entity);
        return UserEntityMapper.toDomain(saved);
    }

    @Override
    public void delete(User user) {
        var entity = UserEntityMapper.toEntity(user);
        userRepository.delete(entity);
    }

    @Override
    public boolean existsBySubType(Long id) {
        return userRepository.existsBytipoUsuarioId(id);
    }

    private Sort toSpringSort(List<SortOrder> orders) {
        return Sort.by(
                orders.stream().map(
                    order -> order.isAscending() ?
                            Sort.Order.asc(order.getProperty()) : Sort.Order.desc(order.getProperty())
        ).toList());
    }
}
