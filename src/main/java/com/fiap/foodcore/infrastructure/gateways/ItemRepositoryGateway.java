package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.gateways.persistence.ItemRepository;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.ItemEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.mapper.ItemMapper;
import com.fiap.foodcore.infrastructure.mapper.UserEntityMapper;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                        .ownerId(UserEntityMapper.toDomain(entity.getOwner()))
                        .build());
    }

    @Override
    public Item save(Item item) {
        ItemEntity entity = ItemMapper.toEntity(item);

        ItemEntity savedEntity = itemRepository.save(entity);
        return ItemMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
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

    @Override
    public Optional<Item> findByNameIgnoreCase(String name) {
        return itemRepository.findByNameIgnoreCase(name).stream()
                .map(ItemMapper::toDomain).findFirst();
    }

    @Override
    public List<Item> findByOwnerId(Long ownerId) {
        return itemRepository.findByOwnerId(ownerId)
                .stream()
                .map(ItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findByIdAndOwnerId(Long id, Long ownerId) {
        return itemRepository.findByIdAndOwnerId(id, ownerId)
                .map(ItemMapper::toDomain);
    }

    @Override
    public boolean existsByIdAndOwnerId(Long id, Long ownerId) {
        return itemRepository.existsByIdAndOwnerId(id, ownerId);
    }

    private Sort toSpringSort(List<SortOrder> orders) {
        return Sort.by(
                orders.stream().map(
                        order -> order.isAscending() ?
                                Sort.Order.asc(order.getProperty()) : Sort.Order.desc(order.getProperty())
                ).toList());
    }
}
