package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.menu.*;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.presenter.MenuPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuCreateResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuUpdateRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapios")
public class MenuControllerImpl implements MenuController {

    private final static Logger logger = LoggerFactory.getLogger(MenuControllerImpl.class);

    private final CreateMenuInteractor createMenuInteractor;
    private final ListMenuInteractor listMenuInteractor;
    private final UpdateMenuInteractor updateMenuInteractor;
    private final DeleteMenuInteractor deleteMenuInteractor;
    private final FindMenuByIdInteractor findMenuByIdInteractor;

    public MenuControllerImpl(CreateMenuInteractor createMenuInteractor, ListMenuInteractor listMenuInteractor, UpdateMenuInteractor updateMenuInteractor, DeleteMenuInteractor deleteMenuInteractor, FindMenuByIdInteractor findMenuByIdInteractor) {
        this.createMenuInteractor = createMenuInteractor;
        this.listMenuInteractor = listMenuInteractor;
        this.updateMenuInteractor = updateMenuInteractor;
        this.deleteMenuInteractor = deleteMenuInteractor;
        this.findMenuByIdInteractor = findMenuByIdInteractor;
    }

    @GetMapping
    public ResponseEntity<Page<MenuCreateResponseDTO>> listMenus(Pageable pageable) {
        logger.info("Handling GET request to /cardapios");
        List<SortOrder> sortOrders = pageable.getSort().stream()
                .map(order -> new SortOrder(order.getProperty(), order.isAscending()))
                .toList();

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize(), sortOrders);

        DomainPage<MenuCreateOutput> outputs = listMenuInteractor.execute(pr);
        List<MenuCreateResponseDTO> dtos = MenuPresenter.toDtoList(outputs.getItems());

        Page<MenuCreateResponseDTO> paginatedUser = new PageImpl<>(
                dtos,
                pageable,
                outputs.getTotalElements()
        );

        return ResponseEntity.ok(paginatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuCreateResponseDTO> findMenuById(@PathVariable Long id) {
        logger.info("Handling GET request to /cardapios with id={}", id);

        MenuCreateOutput output = findMenuByIdInteractor.execute(id);
        MenuCreateResponseDTO dto = MenuPresenter.toResponseDTO(output);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MenuCreateResponseDTO> createMenu(@RequestBody MenuCreateRequestDTO menuDto) {
        logger.info("Handling POST request to /cardapios");

        MenuCreateOutput output = createMenuInteractor.execute(MenuPresenter.fromCreateInputRequestDTO(menuDto));
        MenuCreateResponseDTO dto = MenuPresenter.toResponseDTO(output);

        return ResponseEntity.status(201).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuCreateResponseDTO> updateMenu(@PathVariable Long id, @RequestBody MenuUpdateRequestDTO menuDto) {
        logger.info("Handling PUT request to /cardapios/{}", id);
        MenuCreateOutput output = updateMenuInteractor.execute(id, menuDto);

        MenuCreateResponseDTO response = MenuPresenter.toResponseDTO(output);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MenuCreateResponseDTO> deleteMenu(@PathVariable Long id) {
        logger.info("Handling DELETE request to /cardapios/{}", id);
        deleteMenuInteractor.execute(id);
        return ResponseEntity.noContent().build();
    }


}
