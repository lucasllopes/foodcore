package com.fiap.foodcore.service;

import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDTO buscarPorId(Long id);

    Page<UserResponseDTO> listarUsuarioPaginado(Pageable pageable);

    UserResponseDTO cadastrarUsuario(UserCreateRequestDTO dto);

    UserResponseDTO atualizar(Long id, UserUpdateRequestDTO dto);

    void trocarSenha(Long id, ChangePasswordRequestDTO dto);

    void deletar(Long id);
}
