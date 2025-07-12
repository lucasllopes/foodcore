package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.MenuRepository;
import com.fiap.foodcore.infrastructure.mapper.MenuMapper;


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
        return null;
    }

    @Override
    public Optional<Menu> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Menu save(Menu menu) {
        return null;
    }

    @Override
    public void delete(Menu menu) {

    }
}
