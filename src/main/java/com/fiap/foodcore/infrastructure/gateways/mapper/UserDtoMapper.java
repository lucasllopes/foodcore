package com.fiap.foodcore.infrastructure.gateways.mapper;


import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserType;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.dto.AddressResponseDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.dto.UserUpdateRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoMapper {

    public static User toDomain(UserCreateRequestDTO dto, String encodedPassword, UserType tipo) {
        return User.fromCreateRequest(encodedPassword, tipo, dto);
    }

    public static User toDomain(User existing, UserUpdateRequestDTO dto) {
        existing.updateInformation(dto);
        return existing;
    }

    public static UserResponseDTO toResponseDto(User user) {
        List<AddressResponseDTO> enderecos = user.getAddress().stream()
                .map(end -> new AddressResponseDTO(
                        end.getLogradouro(),
                        end.getNumero(),
                        end.getComplemento(),
                        end.getBairro(),
                        end.getCep(),
                        end.getEstado(),
                        end.getCidade()
                ))
                .collect(Collectors.toList());

        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getTipo(),
                enderecos
        );
    }

    public static User fromCreateDto(String encodedPassword, UserType tipo, UserCreateRequestDTO dto) {
        return toDomain(dto, encodedPassword, tipo);
    }
}
