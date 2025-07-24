package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.CreateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserSubtypeOutput;
import com.fiap.foodcore.infrastructure.presenter.UserSubtypePresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos")
public class UserSubtypeControllerImpl implements UserSubtypeController {

    private final static Logger logger = LoggerFactory.getLogger(UserSubtypeControllerImpl.class);

    private final CreateUserSubtypeUseCase createUserSubtype;
    private final UpdateUserSubtypeUseCase updateUserSubtype;
    private final FindUserSubtypeByIdUseCase findUserSubtypeById;
    private final FindUserSubtypeByNameUseCase findUserSubtypeByNameUseCase;
    private final DeleteUserSubtypeUseCase deleteUserSubtype;

    public UserSubtypeControllerImpl(CreateUserSubtypeUseCase createUserSubtype, UpdateUserSubtypeUseCase updateUserSubtype, FindUserSubtypeByIdUseCase findUserSubtypeById, FindUserSubtypeByNameUseCase findUserSubtypeByNameUseCase, DeleteUserSubtypeUseCase deleteUserSubtype) {
        this.createUserSubtype = createUserSubtype;
        this.updateUserSubtype = updateUserSubtype;
        this.findUserSubtypeById = findUserSubtypeById;
        this.findUserSubtypeByNameUseCase = findUserSubtypeByNameUseCase;
        this.deleteUserSubtype = deleteUserSubtype;
    }


    @PostMapping
    public ResponseEntity<UserTypeResponseDTO> createUserType(@Valid @RequestBody UserTypeRequestDTO dto) {
        logger.info("Handling POST request to /tipos");

        CreateUserSubtypeInput input = UserSubtypePresenter.toInputCreate(dto);
        CreateUserSubtypeOutput output = createUserSubtype.execute(input);

        UserTypeResponseDTO response = UserSubtypePresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserTypeResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserTypeUpdateRequestDTO dto) {
        logger.info("Handling PUT request to /tipos");

        UpdateUserSubtypeInput input = UserSubtypePresenter.toInputUpdate(dto);
        UpdateUserSubtypeOutput output = updateUserSubtype.execute(id, input);

        UpdateUserTypeResponseDTO response = UserSubtypePresenter.toUpdateDto(output);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponseDTO> findById(@PathVariable Long id) {
        logger.info("Handling GET request to /{ID}");

        CreateUserSubtypeOutput output = findUserSubtypeById.execute(id);
        UserTypeResponseDTO dto = UserSubtypePresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserTypeResponseDTO>> findByName(@PathVariable String name) {
        logger.info("Handling GET request to /tipos/name/{}", name);

        List<CreateUserSubtypeOutput> outputList = findUserSubtypeByNameUseCase.execute(name);

        List<UserTypeResponseDTO> responseList = outputList.stream()
                .map(UserSubtypePresenter::toDto)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Handling DELETE request to /tipos");
        deleteUserSubtype.execute(id);
        return ResponseEntity.noContent().build();
    }
}
