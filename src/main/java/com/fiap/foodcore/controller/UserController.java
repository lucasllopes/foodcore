package com.fiap.foodcore.controller;

import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import com.fiap.foodcore.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id  or hasRole('ROLE_DONO')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        logger.info("Handling GET request to /usuarios/{ID}");
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_DONO')")
    public ResponseEntity<Page<UserResponseDTO>> listPaginatedUsers(Pageable pageable) {
        logger.info("Handling GET request to /usuarios");
        Page<UserResponseDTO> usuarios = userService.listPaginatedUsers(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateRequestDTO dto) {
        logger.info("Handling POST request to /usuarios");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDTO dto) {
        logger.info("Handling PUT request to /usuarios");
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Handling DELETE request to /usuarios");
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestBody @Valid ChangePasswordRequestDTO dto
    ) {
        logger.info("Handling PUT request to /usuarios/{id}/senha");
        userService.changePassword(id, dto);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }
}
