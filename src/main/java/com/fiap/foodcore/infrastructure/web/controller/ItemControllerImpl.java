package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.item.*;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.presenter.ItemPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemCreateResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.item.ItemUpdateRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapios/items")
public class ItemControllerImpl implements ItemController {

    private final static Logger logger = LoggerFactory.getLogger(ItemControllerImpl.class);

    private final ListItemInteractor listItemInteractor;
    private final CreateItemInteractor createItemInteractor;
    private final UpdateItemInteractor updateItemInteractor;
    private final DeleteItemInteractor deleteItemInteractor;
    private final FindItemByIdInteractor findItemByIdInteractor;

    public ItemControllerImpl(ListItemInteractor listItemInteractor, CreateItemInteractor createItemInteractor, UpdateItemInteractor updateItemInteractor, DeleteItemInteractor deleteItemInteractor, FindItemByIdInteractor findItemByIdInteractor) {
        this.listItemInteractor = listItemInteractor;
        this.createItemInteractor = createItemInteractor;
        this.updateItemInteractor = updateItemInteractor;
        this.deleteItemInteractor = deleteItemInteractor;
        this.findItemByIdInteractor = findItemByIdInteractor;
    }

    @GetMapping
    public ResponseEntity<Page<ItemCreateResponseDTO>> listItems(Pageable pageable) {
        logger.info("Handling GET request to /cardapios/items");
        List<SortOrder> sortOrders = pageable.getSort().stream()
                .map(order -> new SortOrder(order.getProperty(), order.isAscending()))
                .toList();

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize(), sortOrders);

        DomainPage<ItemCreateOutput> outputs = listItemInteractor.execute(pr);
        List<ItemCreateResponseDTO> dtos = ItemPresenter.toDtoList(outputs.getItems());

        Page<ItemCreateResponseDTO> paginatedUser = new PageImpl<>(
                dtos,
                pageable,
                outputs.getTotalElements()
        );

        return ResponseEntity.ok(paginatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCreateResponseDTO> findById(@PathVariable Long id) {
        logger.info("Handling GET request to /cardapios/items/{}", id);

        ItemCreateOutput output = findItemByIdInteractor.execute(id);
        ItemCreateResponseDTO dto = ItemPresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_DONO')")
    public ResponseEntity<ItemCreateResponseDTO> createItem(@RequestBody ItemCreateRequestDTO itemDto) {
        logger.info("Handling POST request to /cardapios/items");

        ItemCreateOutput output = createItemInteractor.execute(
                ItemPresenter.toInput(itemDto)
        );
        ItemCreateResponseDTO responseDto = ItemPresenter.toDto(output);

        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("@itemSecurity.isOwner(#id, authentication)")
    @PutMapping("/{id}")
    public ResponseEntity<ItemCreateResponseDTO> updateItem(@PathVariable Long id, @RequestBody ItemUpdateRequestDTO itemDto) {
        logger.info("Handling PUT request to /cardapios/items/{}", id);

        ItemCreateOutput output = updateItemInteractor.execute(id, ItemPresenter.toUpdateInput(itemDto));
        ItemCreateResponseDTO responseDto = ItemPresenter.toDto(output);

        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("@itemSecurity.isOwner(#id, authentication)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        logger.info("Handling DELETE request to /cardapios/items/{}", id);

        deleteItemInteractor.execute(id);
        return ResponseEntity.noContent().build();
    }

}
