package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.AssignUserTypeToUserInput;
import com.fiap.foodcore.application.usecase.input.CreateUserInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserInput;
import com.fiap.foodcore.application.usecase.output.CreateUserOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.infrastructure.presenter.UserPresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final FindUserByIdUseCase findById;
    private final ListUserUseCase listUsers;
    private final CreateUserUseCase createUser;
    private final UpdateUserUseCase updateUser;
    private final ChangePasswordUseCase changePassword;
    private final DeleteUserUseCase deleteUser;
    private final AssignUserTypeToUserUseCase assignUserTypeToUser;

    public UserController(FindUserByIdUseCase findById,
                          ListUserUseCase listUsers,
                          CreateUserUseCase createUser,
                          UpdateUserUseCase updateUser,
                          ChangePasswordUseCase changePassword,
                          DeleteUserUseCase deleteUser,
                          AssignUserTypeToUserUseCase assignUserTypeToUser) {
        this.findById = findById;
        this.listUsers = listUsers;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.changePassword = changePassword;
        this.deleteUser = deleteUser;
        this.assignUserTypeToUser = assignUserTypeToUser;
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id  or hasRole('ROLE_DONO')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        logger.info("Handling GET request to /usuarios/{ID}");

        CreateUserOutput output = findById.execute(id);
        UserResponseDTO dto = UserPresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_DONO')")
    public ResponseEntity<Page<UserResponseDTO>> listPaginatedUsers(Pageable pageable) {

        logger.info("Handling GET request to /usuarios");

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());

        DomainPage<CreateUserOutput> outputs = listUsers.execute(pr);
        List<UserResponseDTO> dtos = UserPresenter.toDtoList(outputs.getItems());

        Page<UserResponseDTO> paginatedUser = new PageImpl<>(
                dtos,
                pageable,
                outputs.getTotalElements()
        );

        return ResponseEntity.ok(paginatedUser);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO dto) {
        logger.info("Handling POST request to /usuarios");

        CreateUserInput input = UserPresenter.toInputCreate(dto);
        CreateUserOutput output = createUser.execute(input);

        UserResponseDTO response = UserPresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDTO dto) {
        logger.info("Handling PUT request to /usuarios");

        UpdateUserInput input = UserPresenter.toInputUpdate(dto);
        CreateUserOutput output = updateUser.execute(id, input);

        UserResponseDTO response = UserPresenter.toDto(output);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Handling DELETE request to /usuarios");
        deleteUser.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequestDTO dto
    ) {
        logger.info("Handling PUT request to /usuarios/{id}/senha");

        var changePasswordInput = UserPresenter.toChangePasswordInput(dto);

        changePassword.execute(id, changePasswordInput);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }

    @PutMapping("/{id}/tipo")
    @PreAuthorize("hasRole('ROLE_DONO')")
    public ResponseEntity<UserResponseDTO> assignUserType(
            @PathVariable Long id,
            @RequestBody @Valid AssignUserTypeToUserDTO dto) {

        AssignUserTypeToUserInput input = UserPresenter.toAssignUserTypeToUserInput(dto);
        CreateUserOutput output = assignUserTypeToUser.execute(id, input);

        UserResponseDTO response = UserPresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
