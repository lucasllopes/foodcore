package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.infrastructure.presenter.UserTypePresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserTypeRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.UserTypeResponseDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipos")
public class UserTypeController {

    private final static Logger logger = LoggerFactory.getLogger(UserTypeController.class);

    private final CreateUserTypeInteractor createUserType;

    public UserTypeController(CreateUserTypeInteractor createUserType) {
        this.createUserType = createUserType;
    }

    @PostMapping
    public ResponseEntity<UserTypeResponseDTO> createUserType(@Valid @RequestBody UserTypeRequestDTO dto) {
        logger.info("Handling POST request to /tipos");

        CreateUserTypeInput input = UserTypePresenter.toInputCreate(dto);
        CreateUserTypeOutput output = createUserType.execute(input);

        UserTypeResponseDTO response = UserTypePresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
