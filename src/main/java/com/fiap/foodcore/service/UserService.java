package com.fiap.foodcore.service;

import com.fiap.foodcore.dto.ChangePasswordRequestDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDTO findById(Long id);

    Page<UserResponseDTO> listPaginatedUsers(Pageable pageable);

    UserResponseDTO createUser(UserCreateRequestDTO dto);

    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO dto);

    void changePassword(Long id, ChangePasswordRequestDTO dto);

    void deleteUser(Long id);
}
