package com.fiap.g10.g10auth.mapper;

import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioEntity toEntity(Usuario usuario);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    Usuario toDomain(UsuarioEntity entity);

    @ObjectFactory
    default Usuario criarUsuario(UsuarioEntity entity) {
        return Usuario.reconstruirUsuario(entity);
    }
}
