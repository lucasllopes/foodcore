package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.CreateUserTypeInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserTypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserTypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserTypeOutput;
import com.fiap.foodcore.infrastructure.presenter.UserTypePresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipos")
public class UserTypeController {

    private final static Logger logger = LoggerFactory.getLogger(UserTypeController.class);

    private final CreateUserTypeUseCase createUserType;
    private final UpdateUserTypeUseCase updateUserType;
    private final FindUserTypeByIdUseCase findUserTypeById;
    private final FindUserTypeByNameUseCase findUserTypeByNameUseCase;

    public UserTypeController(CreateUserTypeUseCase createUserType, UpdateUserTypeUseCase updateUserType,
                              FindUserTypeByIdUseCase findUserTypeById,
                              FindUserTypeByNameUseCase findUserTypeByName) {
        this.createUserType = createUserType;
        this.updateUserType = updateUserType;
        this.findUserTypeById = findUserTypeById;
        this.findUserTypeByNameUseCase = findUserTypeByName;
    }

    @PostMapping
    public ResponseEntity<UserTypeResponseDTO> createUserType(@Valid @RequestBody UserTypeRequestDTO dto) {
        logger.info("Handling POST request to /tipos");

        CreateUserTypeInput input = UserTypePresenter.toInputCreate(dto);
        CreateUserTypeOutput output = createUserType.execute(input);

        UserTypeResponseDTO response = UserTypePresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserTypeResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserTypeUpdateRequestDTO dto) {
        logger.info("Handling PUT request to /tipos");

        UpdateUserTypeInput input = UserTypePresenter.toInputUpdate(dto);
        UpdateUserTypeOutput output = updateUserType.execute(id, input);

        UpdateUserTypeResponseDTO response = UserTypePresenter.toUpdateDto(output);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponseDTO> findById(@PathVariable Long id) {
        logger.info("Handling GET request to /usuarios/{ID}");

        CreateUserTypeOutput output = findUserTypeById.execute(id);
        UserTypeResponseDTO dto = UserTypePresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserTypeResponseDTO> findByName(@PathVariable String name) {
        logger.info("Handling GET request to /usuarios/{name}");

        CreateUserTypeOutput output = findUserTypeByNameUseCase.execute(name);
        UserTypeResponseDTO dto = UserTypePresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }
}
