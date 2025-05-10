package com.fiap.g10.g10auth.controller;

import com.fiap.g10.g10auth.dto.TrocarSenhaRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.dto.UsuarioUpdateRequestDTO;
import com.fiap.g10.g10auth.service.UsuarioService;
import com.fiap.g10.g10auth.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        logger.info("Request para /usuarios/{ID} -> GET");
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuarios(Pageable pageable) {
        logger.info("Request para /usuarios -> GET TESTE");
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarioPaginado(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioCreateRequestDTO dto) {
        logger.info("Request para /usuarios -> POST");
        return ResponseEntity.ok(usuarioService.cadastrarUsuario(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateRequestDTO dto) {
        logger.info("Request para /usuarios -> PUT");
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.info("Request para /usuarios -> DELETE");
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<String> trocarSenha(
            @PathVariable Long id,
            @RequestBody @Valid TrocarSenhaRequestDTO dto
    ) {
        logger.info("Request para /usuarios/{id}/senha -> PUT");
        usuarioService.trocarSenha(id, dto);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }
}
