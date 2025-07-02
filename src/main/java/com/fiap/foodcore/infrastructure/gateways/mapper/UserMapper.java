package com.fiap.foodcore.infrastructure.gateways.mapper;

import com.fiap.foodcore.infrastructure.gateways.persistence.entity.UserEntity;
import com.fiap.foodcore.domain.User;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return User.rebuildUser(entity);
    }

    public static UserEntity toEntity(User user) {
        if (user == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setNome(user.getNome());
        entity.setEmail(user.getEmail());
        entity.setLogin(user.getLogin());
        entity.setSenha(user.getSenha());
        entity.setTipo(user.getTipo());
        entity.setDataUltimaAlteracao(user.getDataUltimaAlteracao());
        if (user.getAddress() != null && !user.getAddress().isEmpty()) {
            var addressEntities = user.getAddress()
                    .stream()
                    .map(AddressMapper::toEntity)
                    .peek(ae -> ae.setUsuario(entity))
                    .collect(Collectors.toList());
            entity.setEnderecos(addressEntities);
        }
        return entity;
    }
}
