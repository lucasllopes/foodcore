package com.fiap.g10.g10auth.service.impl;

import com.fiap.g10.g10auth.domain.model.Usuario;
import com.fiap.g10.g10auth.dto.TrocarSenhaRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioCreateRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.dto.UsuarioUpdateRequestDTO;
import com.fiap.g10.g10auth.exception.SenhaIncorretaException;
import com.fiap.g10.g10auth.mapper.UsuarioMapper;
import com.fiap.g10.g10auth.persistence.entity.UsuarioEntity;
import com.fiap.g10.g10auth.exception.DadoDuplicadoException;
import com.fiap.g10.g10auth.exception.DadoNaoEncontradoException;
import com.fiap.g10.g10auth.persistence.repository.UsuarioRepository;
import com.fiap.g10.g10auth.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper mapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado"));

        Usuario usuario = mapper.toDomain(entity);
        return mapper.toResponseDTO(usuario);
    }

    @Override
    public Page<UsuarioResponseDTO> listarUsuarioPaginado(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(mapper::toDomain)
                .map(mapper::toResponseDTO);
    }

    @Override
    public UsuarioResponseDTO cadastrarUsuario(UsuarioCreateRequestDTO dto) {
        validarDuplicidadeParaCriacao(dto);

        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        Usuario usuario = Usuario.novoUsuario(null, senhaCriptografada, dto);

        UsuarioEntity salvo = usuarioRepository.save(mapper.toEntity(usuario));
        return mapper.toResponseDTO(mapper.toDomain(salvo));
    }


    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateRequestDTO dto) {

        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado"));

        validarDuplicidadeParaAtualizacao(dto, id);

        Usuario usuario = mapper.toDomain(entity);

        usuario.atualizarDados(dto);

        UsuarioEntity atualizado = usuarioRepository.save(mapper.toEntity(usuario));
        return mapper.toResponseDTO(mapper.toDomain(atualizado));
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

        Usuario usuario = mapper.toDomain(entity);

        boolean senhaAtualConfere = passwordEncoder.matches(dto.senhaAtual(), usuario.getSenha());
        if (!senhaAtualConfere) {
            throw new SenhaIncorretaException("Senha atual incorreta.");
        }

        String novaSenhaCriptografada = passwordEncoder.encode(dto.novaSenha());
        usuario.trocarSenha(novaSenhaCriptografada);

        usuarioRepository.save(mapper.toEntity(usuario));
    }

    @Override
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new DadoNaoEncontradoException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
