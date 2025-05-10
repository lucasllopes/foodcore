package com.fiap.g10.g10auth.converter;

import com.fiap.g10.g10auth.domain.model.Endereco;
import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.persistence.entity.EnderecoEntity;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;

public class UsuarioConverter {

    public static Usuario fromCreateDto(String senhaCodificada, UsuarioCreateRequestDTO dto) {
        return Usuario.novoUsuario(senhaCodificada, dto);
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        return Usuario.reconstruirUsuario(entity);
    }

    public static UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setLogin(usuario.getLogin());
        entity.setSenha(usuario.getSenha());
        entity.setTipo(usuario.getTipo());
        entity.setDataUltimaAlteracao(usuario.getDataUltimaAlteracao());

        if (usuario.getEndereco() != null) {
            EnderecoEntity enderecoEntity = EnderecoConverter.toEntity(usuario.getEndereco());
            enderecoEntity.setUsuario(entity);
            entity.setEndereco(enderecoEntity);
        }

        return entity;
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        Endereco e = usuario.getEndereco();
        return new UsuarioResponseDTO(
                usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getLogin(),
                usuario.getTipo(), e.getLogradouro(), e.getNumero(), e.getComplemento(),
                e.getBairro(), e.getCep(), e.getEstado(),e.getCidade()
        );
    }
}
