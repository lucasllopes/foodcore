package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.item.CreateItemInteractor;
import com.fiap.foodcore.application.usecase.item.DeleteItemInteractor;
import com.fiap.foodcore.application.usecase.item.ListItemInteractor;
import com.fiap.foodcore.application.usecase.item.UpdateItemInteractor;
import com.fiap.foodcore.application.usecase.output.ItemCreateOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.presenter.ItemPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.ItemCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.ItemCreateResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.ItemUpdateRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapios/items")
public class ItemController {

    private final static Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ListItemInteractor listItemInteractor;
    private final CreateItemInteractor createItemInteractor;
    private final UpdateItemInteractor updateItemInteractor;
    private final DeleteItemInteractor deleteItemInteractor;

    public ItemController(ListItemInteractor listItemInteractor, CreateItemInteractor createItemInteractor, UpdateItemInteractor updateItemInteractor, DeleteItemInteractor deleteItemInteractor) {
        this.listItemInteractor = listItemInteractor;
        this.createItemInteractor = createItemInteractor;
        this.updateItemInteractor = updateItemInteractor;
        this.deleteItemInteractor = deleteItemInteractor;
    }

    @GetMapping
    public ResponseEntity<Page<ItemCreateResponseDTO>> listItems(Pageable pageable) {
        logger.info("Handling GET request to /cardapios/items");

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());

        DomainPage<ItemCreateOutput> outputs = listItemInteractor.execute(pr);
        List<ItemCreateResponseDTO> dtos = ItemPresenter.toDtoList(outputs.getItems());

        Page<ItemCreateResponseDTO> paginatedUser = new PageImpl<>(
                dtos,
                pageable,
                outputs.getTotalElements()
        );

        return ResponseEntity.ok(paginatedUser);
    }

    @PostMapping
    public ResponseEntity<ItemCreateResponseDTO> createItem(@RequestBody ItemCreateRequestDTO itemDto) {
        logger.info("Handling POST request to /cardapios/items");

        ItemCreateOutput output = createItemInteractor.execute(
                ItemPresenter.toInput(itemDto)
        );
        ItemCreateResponseDTO responseDto = ItemPresenter.toDto(output);

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ItemCreateResponseDTO> updateItem(@PathVariable Long id, @RequestBody ItemUpdateRequestDTO itemDto) {
        logger.info("Handling PUT request to /cardapios/items/{}", id);

        ItemCreateOutput output = updateItemInteractor.execute(id, ItemPresenter.toUpdateInput(itemDto));
        ItemCreateResponseDTO responseDto = ItemPresenter.toDto(output);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        logger.info("Handling DELETE request to /cardapios/items/{}", id);

        deleteItemInteractor.execute(id);
        return ResponseEntity.noContent().build();
    }


}
