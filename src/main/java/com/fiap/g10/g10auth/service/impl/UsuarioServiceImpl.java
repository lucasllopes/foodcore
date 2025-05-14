package com.fiap.g10.g10auth.service.impl;

import com.fiap.g10.g10auth.converter.UsuarioConverter;
import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.dto.TrocarSenhaRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.dto.UsuarioUpdateRequestDTO;
import com.fiap.g10.g10auth.exception.SenhaIncorretaException;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import com.fiap.g10.g10auth.exception.DadoDuplicadoException;
import com.fiap.g10.g10auth.exception.DadoNaoEncontradoException;
import com.fiap.g10.g10auth.persistence.repository.UsuarioRepository;
import com.fiap.g10.g10auth.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado"));

        Usuario usuario = UsuarioConverter.toDomain(entity);
        return UsuarioConverter.toResponseDTO(usuario);
    }


    @Override
    public Page<UsuarioResponseDTO> listarUsuarioPaginado(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(UsuarioConverter::toDomain)
                .map(UsuarioConverter::toResponseDTO);
    }

    @Override
    public UsuarioResponseDTO cadastrarUsuario(UsuarioCreateRequestDTO dto) {
        validarDuplicidadeParaCriacao(dto);

        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        Usuario usuario = UsuarioConverter.fromCreateDto(senhaCriptografada, dto);

        UsuarioEntity salvo = usuarioRepository.save(UsuarioConverter.toEntity(usuario));
        return UsuarioConverter.toResponseDTO(UsuarioConverter.toDomain(salvo));
    }


    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateRequestDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado."));

        Usuario usuario = UsuarioConverter.toDomain(entity);

        validarDuplicidadeParaAtualizacao(dto, id);

        usuario.atualizarDados(dto);
        UsuarioEntity atualizado = usuarioRepository.save(UsuarioConverter.toEntity(usuario));

        return UsuarioConverter.toResponseDTO(UsuarioConverter.toDomain(atualizado));
    }

    private void validarDuplicidadeParaCriacao(UsuarioCreateRequestDTO dto) {
        usuarioRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new DadoDuplicadoException("Email já está em uso.");
        });

        usuarioRepository.findByLogin(dto.login()).ifPresent(u -> {
            throw new DadoDuplicadoException("Login já está em uso.");
        });
    }

    private void validarDuplicidadeParaAtualizacao(UsuarioUpdateRequestDTO dto, Long idAtual) {
        usuarioRepository.findByEmail(dto.email()).ifPresent(u -> {
            if (!u.getId().equals(idAtual)) {
                throw new DadoDuplicadoException("Email já está em uso por outro usuário.");
            }
        });
    }

    @Override
    public void trocarSenha(Long id, TrocarSenhaRequestDTO dto) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado"));

        Usuario usuario = UsuarioConverter.toDomain(entity);

        boolean senhaAtualConfere = passwordEncoder.matches(dto.senhaAtual(), usuario.getSenha());
        if (!senhaAtualConfere) {
            throw new SenhaIncorretaException("Senha atual incorreta.");
        }

        String novaSenhaCriptografada = passwordEncoder.encode(dto.novaSenha());
        usuario.trocarSenha(novaSenhaCriptografada);

        usuarioRepository.save(UsuarioConverter.toEntity(usuario));
    }

    @Override
    public void deletar(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado."));

        //usuario.setEndereco(null);
        usuarioRepository.save(usuario);

        usuarioRepository.delete(usuario);
    }

}
