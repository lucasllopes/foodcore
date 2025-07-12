package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.CreateRestaurantInput;
import com.fiap.foodcore.application.usecase.output.CreateRestaurantOutput;
import com.fiap.foodcore.infrastructure.presenter.RestaurantPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantController {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private final CreateRestaurantInteractor createRestaurantInteractor;

    public RestaurantController(CreateRestaurantInteractor createRestaurantInteractor) {
        this.createRestaurantInteractor = createRestaurantInteractor;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createUser(@Valid @RequestBody RestaurantCreateRequestDTO dto) {
        logger.info("Handling POST request to /restaurantes");

        CreateRestaurantInput input = RestaurantPresenter.toInputCreate(dto);
        CreateRestaurantOutput output = createRestaurantInteractor.execute(input);

        RestaurantResponseDTO response = RestaurantPresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
