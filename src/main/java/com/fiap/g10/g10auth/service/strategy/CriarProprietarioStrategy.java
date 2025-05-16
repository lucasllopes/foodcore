package com.fiap.g10.g10auth.service.strategy;


import com.fiap.g10.g10auth.converter.UsuarioConverter;
import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.exception.DadoDuplicadoException;
import com.fiap.g10.g10auth.persistence.entity.TipoUsuario;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import com.fiap.g10.g10auth.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Qualifier("dono")
public class CriarProprietarioStrategy implements CriarUsuarioStrategy {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CriarProprietarioStrategy(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponseDTO criar(UsuarioCreateRequestDTO dto) {
        validarDuplicidadeParaCriacao(dto);

        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        Usuario usuario = UsuarioConverter.fromCreateDto(senhaCriptografada, TipoUsuario.DONO, dto);

        UsuarioEntity salvo = usuarioRepository.save(UsuarioConverter.toEntity(usuario));
        return UsuarioConverter.toResponseDTO(UsuarioConverter.toDomain(salvo));
    }

    private void validarDuplicidadeParaCriacao(UsuarioCreateRequestDTO dto) {
        usuarioRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new DadoDuplicadoException("Email já está em uso.");
        });

        usuarioRepository.findByLogin(dto.login()).ifPresent(u -> {
            throw new DadoDuplicadoException("Login já está em uso.");
        });
    }

}

