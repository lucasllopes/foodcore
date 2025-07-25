package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.MenuGateway;
import com.fiap.foodcore.domain.Menu;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.gateways.persistence.ItemRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.MenuRepository;
import com.fiap.foodcore.infrastructure.mapper.MenuMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class MenuRepositoryGateway implements MenuGateway {

    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;

    public MenuRepositoryGateway(MenuRepository menuRepository, ItemRepository itemRepository) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id)
                .map(MenuMapper::toDomain);
    }

    @Override
    public DomainPage<Menu> findAll(PageRequestDomain pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.page(), pageRequest.size(), toSpringSort(pageRequest.sortOrders()));

        var springPage = menuRepository.findAll(pageable);

        var items = springPage.getContent().stream()
                .map(MenuMapper::toDomain)
                .toList();

        return new DomainPage<>(items,
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements());
    }


    @Override
    public Menu save(Menu menu) {
        var entity = MenuMapper.toEntity(menu);

        var persistedItems = entity.getItems().stream()
                .map(item -> item.getId() == null ? itemRepository.save(item) : item)
                .toList();

        entity.setItems(persistedItems);

        var saved = menuRepository.save(entity);
        return MenuMapper.toDomain(saved);
    }

    @Override
    public void delete(Menu menu) {
        var entity = MenuMapper.toEntity(menu);
        menuRepository.delete(entity);
    }

    @Override
    public Optional<Menu> findByName(String name) {
        return menuRepository.findByName(name)
                .map(MenuMapper::toDomain);
    }

    @Override
    public Optional<Menu> findByNameAndRestaurantId(String name, Long restaurantId) {
        return menuRepository.findByNameAndRestaurantId(name, restaurantId)
                .map(MenuMapper::toDomain);
    }

    private Sort toSpringSort(List<SortOrder> orders) {
        return Sort.by(
                orders.stream().map(
                        order -> order.isAscending() ?
                                Sort.Order.asc(order.getProperty()) : Sort.Order.desc(order.getProperty())
                ).toList());
    }

}
