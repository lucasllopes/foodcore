package com.fiap.g10.g10auth.service;

import com.fiap.g10.g10auth.dto.TrocarSenhaRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioRequestDTO;
import com.fiap.g10.g10auth.dto.UsuarioResponseDTO;
import com.fiap.g10.g10auth.entity.Usuario;
import com.fiap.g10.g10auth.exception.DadoDuplicadoException;
import com.fiap.g10.g10auth.exception.DadoNaoEncontradoException;
import com.fiap.g10.g10auth.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado"));
    }

    public Page<UsuarioResponseDTO> listarUsuarioPaginado(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::converterUsuarioParaResponseDTO);
    }

    public Usuario cadastrarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new DadoDuplicadoException("Email já está em uso.");
        }

        if (usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new DadoDuplicadoException("Login já está em uso.");
        }

        Usuario usuario = Usuario.novoUsuario(dto, passwordEncoder);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado"));

        usuarioRepository.findByEmail(dto.email()).ifPresent(u -> {
            if (!u.getId().equals(id)) {
                throw new DadoDuplicadoException("Email já está em uso por outro usuário.");
            }
        });

        usuarioRepository.findByLogin(dto.login()).ifPresent(u -> {
            if (!u.getId().equals(id)) {
                throw new DadoDuplicadoException("Login já está em uso por outro usuário.");
            }
        });

        usuarioExistente.atualizarDados(dto);
        return usuarioRepository.save(usuarioExistente);
    }


    public UsuarioResponseDTO converterUsuarioParaResponseDTO(Usuario usuario) {
        var e = usuario.getEndereco();
        return new UsuarioResponseDTO(
                usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getLogin(),
                usuario.getTipo(), e.getLogradouro(), e.getNumero(), e.getComplemento(),
                e.getBairro(), e.getCep()
        );
    }

    public void trocarSenha(Long id, TrocarSenhaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("Usuário não encontrado"));

        usuario.trocarSenha(dto.senhaAtual(), dto.novaSenha(), passwordEncoder);

        usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new DadoNaoEncontradoException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
