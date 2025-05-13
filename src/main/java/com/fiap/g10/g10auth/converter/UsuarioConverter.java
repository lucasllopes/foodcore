package com.fiap.g10.g10auth.converter;

import com.fiap.g10.g10auth.domain.model.Endereco;
import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.dto.EnderecoResponseDTO;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.persistence.entity.EnderecoEntity;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;

import java.util.List;
import java.util.stream.Collectors;

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

        if(usuario.getEndereco() != null && !usuario.getEndereco().isEmpty()){
            List<EnderecoEntity> enderecoEntities = usuario.getEndereco()
                    .stream()
                    .map(endereco -> {
                        EnderecoEntity enderecoEntity = EnderecoConverter.toEntity(endereco);
                        enderecoEntity.setUsuario(entity); // associa o usuário à entidade de endereço
                        return enderecoEntity;
                    })
                    .collect(Collectors.toList());

            entity.setEnderecos(enderecoEntities);
        }

        return entity;
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {

        List<EnderecoResponseDTO> enderecos = usuario.getEndereco()
                .stream()
                .map(e -> new EnderecoResponseDTO(
                        e.getLogradouro(), e.getNumero(), e.getComplemento(),
                        e.getBairro(), e.getCep(), e.getEstado(), e.getCidade()
                ))
                .collect(Collectors.toList());

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipo(),
                enderecos
        );
    }
}
