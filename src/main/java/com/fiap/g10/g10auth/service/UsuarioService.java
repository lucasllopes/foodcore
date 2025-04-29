package com.fiap.g10.g10auth.service;

import com.fiap.g10.g10auth.dto.TrocarSenhaRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.dto.UsuarioUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    UsuarioResponseDTO buscarPorId(Long id);

    Page<UsuarioResponseDTO> listarUsuarioPaginado(Pageable pageable);

    UsuarioResponseDTO cadastrarUsuario(UsuarioCreateRequestDTO dto);

    UsuarioResponseDTO atualizar(Long id, UsuarioUpdateRequestDTO dto);

    void trocarSenha(Long id, TrocarSenhaRequestDTO dto);

    void deletar(Long id);
}
