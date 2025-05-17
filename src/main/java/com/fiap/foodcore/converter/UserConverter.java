package com.fiap.foodcore.converter;

import com.fiap.foodcore.domain.model.User;
import com.fiap.foodcore.dto.AddressResponseDTO;
import com.fiap.foodcore.dto.UserCreateRequestDTO;
import com.fiap.foodcore.dto.UserResponseDTO;
import com.fiap.foodcore.persistence.entity.AddressEntity;
import com.fiap.foodcore.persistence.entity.UserType;
import com.fiap.foodcore.persistence.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static User fromCreateDto(String senhaCodificada, UserType tipo, UserCreateRequestDTO dto) {
        return User.novoUsuario(senhaCodificada, tipo, dto);
    }

    public static User toDomain(UserEntity entity) {
        return User.reconstruirUsuario(entity);
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setNome(user.getNome());
        entity.setEmail(user.getEmail());
        entity.setLogin(user.getLogin());
        entity.setSenha(user.getSenha());
        entity.setTipo(user.getTipo());
        entity.setDataUltimaAlteracao(user.getDataUltimaAlteracao());

        if(!user.getAddress().isEmpty()){
            List<AddressEntity> enderecoEntities = user.getAddress()
                    .stream()
                    .map(endereco -> {
                        AddressEntity addressEntity = AddressConverter.toEntity(endereco);
                        addressEntity.setUsuario(entity); // associa o usuário à entidade de endereço
                        return addressEntity;
                    })
                    .collect(Collectors.toList());

            entity.setEnderecos(enderecoEntities);
        }

        return entity;
    }

    public static UserResponseDTO toResponseDTO(User user) {

        List<AddressResponseDTO> enderecos = user.getAddress()
                .stream()
                .map(e -> new AddressResponseDTO(
                        e.getLogradouro(), e.getNumero(), e.getComplemento(),
                        e.getBairro(), e.getCep(), e.getEstado(), e.getCidade()
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
}
