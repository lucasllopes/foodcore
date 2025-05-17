package com.fiap.foodcore.controller;

import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import com.fiap.foodcore.service.UserService;
import com.fiap.foodcore.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserServiceImpl usuarioService) {
        this.userService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id) {
        logger.info("Request para /usuarios/{ID} -> GET");
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> listarUsuarios(Pageable pageable) {
        logger.info("Request para /usuarios -> GET");
        Page<UserResponseDTO> usuarios = userService.listarUsuarioPaginado(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> cadastrar(@RequestBody @Valid UserCreateRequestDTO dto) {
        logger.info("Request para /usuarios -> POST");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.cadastrarUsuario(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDTO dto) {
        logger.info("Request para /usuarios -> PUT");
        return ResponseEntity.ok(userService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.info("Request para /usuarios -> DELETE");
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<String> trocarSenha(
            @PathVariable Long id,
            @RequestBody @Valid ChangePasswordRequestDTO dto
    ) {
        logger.info("Request para /usuarios/{id}/senha -> PUT");
        userService.trocarSenha(id, dto);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }
}
