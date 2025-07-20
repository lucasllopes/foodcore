package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.menu.CreateMenuInteractor;
import com.fiap.foodcore.application.usecase.menu.ListMenuInteractor;
import com.fiap.foodcore.application.usecase.output.MenuCreateOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.presenter.MenuPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.MenuCreateResponseDTO;
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
public class MenuController {

    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final CreateMenuInteractor createMenuInteractor;
    private final ListMenuInteractor listMenuInteractor;

    public MenuController(CreateMenuInteractor createMenuInteractor, ListMenuInteractor listMenuInteractor) {
        this.createMenuInteractor = createMenuInteractor;
        this.listMenuInteractor = listMenuInteractor;
    }

    @GetMapping
    public ResponseEntity<Page<MenuCreateResponseDTO>> listMenus(Pageable pageable) {
        logger.info("Handling GET request to /cardapios");

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());

        DomainPage<MenuCreateOutput> outputs = listMenuInteractor.execute(pr);
        List<MenuCreateResponseDTO> dtos = MenuPresenter.toDtoList(outputs.getItems());

        Page<MenuCreateResponseDTO> paginatedUser = new PageImpl<>(
                dtos,
                pageable,
                outputs.getTotalElements()
        );

        return ResponseEntity.ok(paginatedUser);
    }

    @PostMapping
    public ResponseEntity<MenuCreateResponseDTO> createMenu(@RequestBody  MenuCreateRequestDTO menuDto) {
        logger.info("Handling POST request to /cardapios");

        MenuCreateOutput output = createMenuInteractor.execute(MenuPresenter.fromCreateInputRequestDTO(menuDto));
        MenuCreateResponseDTO dto = MenuPresenter.toResponseDTO(output);

        return ResponseEntity.status(201).body(dto);
    }

}
