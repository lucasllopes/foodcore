package com.fiap.foodcore.controller;

import com.fiap.foodcore.core.model.domain.DomainPage;
import com.fiap.foodcore.core.model.domain.PageRequestDomain;
import com.fiap.foodcore.core.port.in.*;
import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
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

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final FindUserByIdUseCase findById;
    private final ListUsersUseCase listUsers;
    private final CreateUsersUseCase createUser;
    private final UpdateUserUseCase updateUser;
    private final ChangePasswordUseCase changePassword;
    private final DeleteUserUseCase deleteUser;

    public UserController(FindUserByIdUseCase findById,
                          ListUsersUseCase listUsers,
                          CreateUsersUseCase createUser,
                          UpdateUserUseCase updateUser,
                          ChangePasswordUseCase changePassword,
                          DeleteUserUseCase deleteUser) {
        this.findById       = findById;
        this.listUsers      = listUsers;
        this.createUser     = createUser;
        this.updateUser     = updateUser;
        this.changePassword = changePassword;
        this.deleteUser     = deleteUser;
    }


    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id  or hasRole('ROLE_DONO')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        logger.info("Handling GET request to /usuarios/{ID}");
        return ResponseEntity.ok(findById.execute(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_DONO')")
    public ResponseEntity<Page<UserResponseDTO>> listPaginatedUsers(Pageable pageable) {
        logger.info("Handling GET request to /usuarios");

        //TODO
        // TER METODO ESTATICO?
        // REMOVER VALIDACOES DE VALORES ZERADOS
                PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize());

        DomainPage<UserResponseDTO> users = listUsers.execute(pr);

        var paginatedUser = new PageImpl<>(
                users.getItems(),
                pageable,
                users.getTotalElements()
        );

        return ResponseEntity.ok(paginatedUser);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO dto) {
        logger.info("Handling POST request to /usuarios");
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser.execute(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDTO dto) {
        logger.info("Handling PUT request to /usuarios");
        return ResponseEntity.ok(updateUser.execute(id, dto));
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
        changePassword.execute(id, dto);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }
}
