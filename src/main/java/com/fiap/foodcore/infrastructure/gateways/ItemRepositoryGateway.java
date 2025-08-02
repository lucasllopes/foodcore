package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.gateways.persistence.ItemRepository;
import com.fiap.foodcore.infrastructure.mapper.ItemMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class ItemRepositoryGateway implements ItemGateway {

    private final ItemRepository itemRepository;

    public ItemRepositoryGateway(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public DomainPage<Item> findAll(PageRequestDomain pageRequest){
        Pageable pageable = PageRequest.of(pageRequest.page(), pageRequest.size(), toSpringSort(pageRequest.sortOrders()));

        var page = itemRepository.findAll(pageable);

        var items = page.getContent().stream()
                .map(ItemMapper::toDomain)
                .toList();

        return new DomainPage<>(
                items,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id)
                .map(entity -> new Item.Builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .price(entity.getPrice())
                        .availability(entity.getAvailability())
                        .photo(entity.getPhoto())
                        .build());
    }

    @Override
    public Item save(Item item) {
        var entity = ItemMapper.toEntity(item);
        var savedEntity = itemRepository.save(entity);
        return ItemMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Item item) {
        var entity = ItemMapper.toEntity(item);
        itemRepository.delete(entity);
    }

    @Override
    public Optional<Item> findByName(String name) {
        return itemRepository.findByName(name)
                .map(ItemMapper::toDomain);
    }

    @Override
    public List<Item> findItemsByMenuId(Long menuId) {
        return itemRepository.findByMenus_Id(menuId)
                .stream()
                .map(ItemMapper::toDomain)
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
