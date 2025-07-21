package com.fiap.foodcore.infrastructure.gateways;

import com.fiap.foodcore.application.gateway.ItemGateway;
import com.fiap.foodcore.domain.Item;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.ItemRepository;
import com.fiap.foodcore.infrastructure.mapper.ItemMapper;

import java.util.Optional;

public class ItemRepositoryGateway implements ItemGateway {

    private final ItemRepository itemRepository;

    public ItemRepositoryGateway(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public DomainPage<Item> findAll(PageRequestDomain pageRequest){
        var page = itemRepository.findAll(   org.springframework.data.domain.PageRequest
                .of(pageRequest.page(), pageRequest.size()));
        var items = page.getContent().stream()
                .map(ItemMapper::toDomain)
                .toList();
        return new DomainPage<>(items, page.getTotalElements());
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
}
