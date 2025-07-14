package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.Restaurant;
import com.fiap.foodcore.infrastructure.presenter.RestaurantPresenter;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantController {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private final CreateRestaurantInteractor createRestaurantInteractor;
    private final FindRestaurantByIdInteractor findRestaurantByIdInteractor;
    private final DeleteRestaurantInteractor deleteRestaurantInteractor;

    public RestaurantController(CreateRestaurantInteractor createRestaurantInteractor, FindRestaurantByIdInteractor findRestaurantByIdInteractor, DeleteRestaurantInteractor deleteRestaurantInteractor) {
        this.createRestaurantInteractor = createRestaurantInteractor;
        this.findRestaurantByIdInteractor = findRestaurantByIdInteractor;
        this.deleteRestaurantInteractor = deleteRestaurantInteractor;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantCreateRequestDTO dto) {
        logger.info("Handling POST request to /restaurantes");

        CreateRestaurantInput input = RestaurantPresenter.toInputCreate(dto);
        CreateRestaurantOutput output = createRestaurantInteractor.execute(input);

        RestaurantResponseDTO response = RestaurantPresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> findRestaurantById(@PathVariable Long id) {
        logger.info("Handling GET request to /restaurantes/{ID}");

        CreateRestaurantOutput output = findRestaurantByIdInteractor.execute(id);
        RestaurantResponseDTO dto = RestaurantPresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        logger.info("Handling DELETE request to /restaurantes");
        deleteRestaurantInteractor.execute(id);
        return ResponseEntity.noContent().build();
    }
}
