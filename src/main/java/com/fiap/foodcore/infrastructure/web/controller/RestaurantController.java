package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.presenter.RestaurantPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantController {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private final CreateRestaurantInteractor createRestaurantInteractor;
    private final FindRestaurantByIdInteractor findRestaurantByIdInteractor;
    private final DeleteRestaurantInteractor deleteRestaurantInteractor;
    private final ListRestaurantInteractor listRestaurantInteractor;
    private final UpdateRestaurantInteractor updateRestaurantInteractor;

    public RestaurantController(CreateRestaurantInteractor createRestaurantInteractor,
                                FindRestaurantByIdInteractor findRestaurantByIdInteractor,
                                DeleteRestaurantInteractor deleteRestaurantInteractor,
                                ListRestaurantInteractor listRestaurantInteractor,
                                UpdateRestaurantInteractor updateRestaurantInteractor) {
        this.createRestaurantInteractor = createRestaurantInteractor;
        this.findRestaurantByIdInteractor = findRestaurantByIdInteractor;
        this.deleteRestaurantInteractor = deleteRestaurantInteractor;
        this.listRestaurantInteractor = listRestaurantInteractor;
        this.updateRestaurantInteractor = updateRestaurantInteractor;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> findRestaurantById(@PathVariable Long id) {
        logger.info("Handling GET request to /restaurantes/{ID}");

        CreateRestaurantOutput output = findRestaurantByIdInteractor.execute(id);
        RestaurantResponseDTO dto = RestaurantPresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponseDTO>> listPaginatedRestaurants(Pageable pageable) {

        logger.info("Handling GET request to /restaurantes");

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());

        DomainPage<CreateRestaurantOutput> outputs = listRestaurantInteractor.execute(pr);
        List<RestaurantResponseDTO> dtos = RestaurantPresenter.toDtoList(outputs.getItems());

        Page<RestaurantResponseDTO> paginatedRestaurant = new PageImpl<>(
                dtos,
                pageable,
                outputs.getTotalElements()
        );

        return ResponseEntity.ok(paginatedRestaurant);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantCreateRequestDTO dto) {
        logger.info("Handling POST request to /restaurantes");

        CreateRestaurantInput input = RestaurantPresenter.toInputCreate(dto);
        CreateRestaurantOutput output = createRestaurantInteractor.execute(input);

        RestaurantResponseDTO response = RestaurantPresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantUpdateRequestDTO dto) {
        logger.info("Handling PUT request to /restaurantes");

        UpdateRestaurantInput input = RestaurantPresenter.toInputUpdate(dto);
        CreateRestaurantOutput output = updateRestaurantInteractor.execute(id, input);

        RestaurantResponseDTO response = RestaurantPresenter.toDto(output);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        logger.info("Handling DELETE request to /restaurantes");
        deleteRestaurantInteractor.execute(id);
        return ResponseEntity.noContent().build();
    }
}
