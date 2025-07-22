package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.input.restaurant.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.input.restaurant.UpdateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.restaurant.*;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.presenter.RestaurantPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantUpdateRequestDTO;
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
public class RestaurantControllerImpl implements RestaurantController {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantControllerImpl.class);

    private final CreateRestaurantUseCase createRestaurantInteractor;
    private final FindRestaurantByIdUseCase findRestaurantByIdInteractor;
    private final DeleteRestaurantUseCase deleteRestaurantInteractor;
    private final ListRestaurantUseCase listRestaurantInteractor;
    private final UpdateRestaurantUseCase updateRestaurantInteractor;

    public RestaurantControllerImpl(CreateRestaurantUseCase createRestaurantInteractor,
                                    FindRestaurantByIdUseCase findRestaurantByIdInteractor,
                                    DeleteRestaurantUseCase deleteRestaurantInteractor,
                                    ListRestaurantUseCase listRestaurantInteractor,
                                    UpdateRestaurantUseCase updateRestaurantInteractor) {
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
    public ResponseEntity<Page<RestaurantResponseDTO>> listPaginatedRestaurants(@RequestParam(required = false) String name, Pageable pageable) {

        logger.info("Handling GET request to /restaurantes");

        List<SortOrder> sortOrders = pageable.getSort().stream()
                .map(order -> new SortOrder(order.getProperty(), order.isAscending()))
                .toList();

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize(), sortOrders);

        DomainPage<CreateRestaurantOutput> outputs = listRestaurantInteractor.execute(name, pr);
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
