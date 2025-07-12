package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.MenuRepository;
import com.fiap.foodcore.infrastructure.mapper.MenuMapper;
import com.fiap.foodcore.infrastructure.mapper.UserEntityMapper;


import java.util.Optional;

public class MenuRepositoryGateway implements MenuGateway {

    private final MenuRepository menuRepository;

    public MenuRepositoryGateway(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id)
                .map(MenuMapper::toDomain);
    }

    @Override
    public DomainPage<Menu> findAll(PageRequestDomain pageRequest) {

        var springPage = menuRepository.findAll(
                org.springframework.data.domain.PageRequest
                        .of(pageRequest.page(), pageRequest.size())
        );
        var items = springPage.getContent().stream()
                .map(MenuMapper::toDomain)
                .toList();
        return new DomainPage<>(items, springPage.getTotalElements());
    }


    @Override
    public Menu save(Menu menu) {
        var entity = MenuMapper.toEntity(menu);
        var saved = menuRepository.save(entity);
        return MenuMapper.toDomain(saved);
    }

    @Override
    public void delete(Menu menu) {
        var entity = MenuMapper.toEntity(menu);
        menuRepository.delete(entity);
    }
}
