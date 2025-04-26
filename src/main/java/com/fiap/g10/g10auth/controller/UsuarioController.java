package com.fiap.g10.g10auth.controller;

import com.fiap.g10.g10auth.dto.TrocarSenhaRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.entity.Usuario;
import com.fiap.g10.g10auth.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioService.converterUsuarioParaResponseDTO(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuarios(Pageable pageable) {
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarioPaginado(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario usuario = usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.ok(usuarioService.converterUsuarioParaResponseDTO(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario usuario = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(usuarioService.converterUsuarioParaResponseDTO(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<String> trocarSenha(
            @PathVariable Long id,
            @RequestBody @Valid TrocarSenhaRequestDTO dto
    ) {
        usuarioService.trocarSenha(id, dto);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }
}
