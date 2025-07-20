package com.fiap.foodcore.infrastructure.mapper;

import com.fiap.foodcore.application.usecase.mapper.UserMapper;
import com.fiap.foodcore.domain.User;
import com.fiap.foodcore.domain.UserTypeDomain;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserType;

import java.util.stream.Collectors;

public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return User.rebuildUserWithType(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getSenha(),
                UserTypeDomain.valueOf(entity.getTipo().name()),
                AddressEntityMapper.toDomain(entity.getEnderecos()),
                entity.getDataUltimaAlteracao(),
                UserTypeEntityMapper.toDomain(entity.getTipoUsuario())
        );
    }

    public static UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setNome(user.getNome());
        entity.setEmail(user.getEmail());
        entity.setLogin(user.getLogin());
        entity.setSenha(user.getSenha());
        entity.setTipo(UserType.fromString(user.getTipo().name()));
        entity.setDataUltimaAlteracao(user.getDataUltimaAlteracao());

        if (user.getAddress() != null && !user.getAddress().isEmpty()) {
            var addressEntities = user.getAddress()
                    .stream()
                    .map(AddressEntityMapper::toEntity)
                    .peek(ae -> ae.setUsuario(entity))
                    .collect(Collectors.toList());

            entity.setEnderecos(addressEntities);
        }

        if(user.getUserType() != null){
            entity.setTipoUsuario(UserTypeEntityMapper.toEntity(user.getUserType()));
        }

        return entity;
    }

    public static User rebuildUserForTokenAuth(UserEntity entity) {
        if (entity == null) return null;

        return User.rebuildForAuthentication(
                entity.getId(),
                entity.getLogin(),
                entity.getSenha(),
                UserTypeDomain.valueOf(entity.getTipo().name())
        );
    }
}
